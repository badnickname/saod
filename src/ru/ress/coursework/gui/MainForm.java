package ru.ress.coursework.gui;

import ru.ress.coursework.core.Base;
import ru.ress.coursework.core.compression.Utils;
import ru.ress.coursework.core.compression.FCoding;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by ress on 28.09.17.
 */
public class MainForm extends JFrame {
    private DefaultListModel<String> lmName, lmDate, lmLaw;
    private DefaultListModel<Integer> lmDep, lmNumb;

    private JList<String> lName;
    private JList<String> lDate;
    private JList<String> lLaw;
    private JList<Integer> lDep, lNumb;

    private JTextField tfSize, tfFind, tfFindTree;
    private JButton btnSize, btnSortDep, btnSortDate, btnLeft, btnRight, btnFind, btnFindTree, btnCompress, btnGetSource;
    private Base base;

    private int elmCount;
    private int curIndex;

    public MainForm(String title, Base base) throws HeadlessException {
        super(title);
        this.base = base;

        Container cont = this.getContentPane();
        cont.setLayout( new GridLayout(1,2 ));

        JPanel leftPanel = new JPanel();
        JPanel botPanel = new JPanel( new GridLayout(2,1));

        JPanel buildPanel = new JPanel();
        JPanel sortPanel = new JPanel();
        JPanel findPanel = new JPanel();
        JPanel findTreePanel = new JPanel();

        btnLeft = new JButton("<<");
        btnLeft.addActionListener(new NavigationButtonListener(this, NavigationButtonListener.left));
        btnRight = new JButton(">>");
        btnRight.addActionListener(new NavigationButtonListener(this, NavigationButtonListener.right));

        tfSize = new JTextField("20",10);
        btnSize = new JButton("Загрузить");
        btnSize.addActionListener(new BuildButtonListener(this));

        btnSortDate = new JButton("Отсортировать по дате");
        btnSortDate.addActionListener(new SortButtonListener(SortButtonListener.date));
        btnSortDep = new JButton("Отсортировать по вкладу");
        btnSortDep.addActionListener(new SortButtonListener(SortButtonListener.deposit));

        tfFind = new JTextField("15000",10);
        btnFind = new JButton("Найти");
        btnFind.addActionListener(new FindButtonListener());
        btnFind.setEnabled(false);
        tfFind.setEnabled(false);

        tfFindTree = new JTextField("15000",10);
        btnFindTree = new JButton("Найти (BTree)");
        btnFindTree.addActionListener(new FindTreeButtonListener());
        btnFindTree.setEnabled(false);
        tfFindTree.setEnabled(false);

        btnCompress = new JButton("СЖАТЬ");
        btnCompress.addActionListener(new CompressButtonListener());

        btnGetSource = new JButton("Исходный");
        btnGetSource.addActionListener(new GetSourceButtonListener());


        lmName = new DefaultListModel<String>();
        lmDep = new DefaultListModel<Integer>();
        lmDate = new DefaultListModel<String>();
        lmLaw = new DefaultListModel<String>();
        lmNumb = new DefaultListModel<Integer>();

        lName = new JList<String>(lmName);
        lDep = new JList<Integer>(lmDep);
        lDate = new JList<String>(lmDate);
        lLaw = new JList<String>(lmLaw);
        lNumb = new JList<Integer>(lmNumb);

        leftPanel.add(lNumb);
        leftPanel.add(lName);
        leftPanel.add(lDep);
        leftPanel.add(lDate);
        leftPanel.add(lLaw);

        buildPanel.add(new JLabel("Размер: "));
        buildPanel.add(tfSize);
        buildPanel.add(btnSize);
        buildPanel.add(btnLeft);
        buildPanel.add(btnRight);

        sortPanel.add(btnSortDate);
        sortPanel.add(btnSortDep);
        sortPanel.add(btnGetSource);
        sortPanel.add(btnCompress);

        findPanel.add(tfFind);
        findPanel.add(btnFind);
        findTreePanel.add(tfFindTree);
        findTreePanel.add(btnFindTree);

        botPanel.add(buildPanel);
        botPanel.add(sortPanel);
        botPanel.add(findPanel);
        botPanel.add(findTreePanel);

        cont.add(leftPanel);
        cont.add(new JScrollPane(leftPanel));
        cont.add(botPanel);

        this.setBounds(0, 0, 1024, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void printList(int count) {
        if (count < 0) curIndex += count*2;
        if (curIndex<0) curIndex = 0;

        int len = curIndex + Math.abs(count);
        if (len > elmCount) len = elmCount;

        int i = curIndex;
        for(; i<len; i++) {
            addElement(i+1, base.getName(i), base.getDeposit(i), base.getDate(i), base.getLawyer(i));
        }
        curIndex = i;

        return;
    }

    private void clearList() {
        lmDep.clear();
        lmDate.clear();
        lmLaw.clear();
        lmNumb.clear();
        lmName.clear();
        return;
    }

    private void addElement(int numb, String name, int deposit, String date, String law) {
        lmNumb.addElement(numb);
        lmName.addElement(name);
        lmDep.addElement(deposit);
        lmDate.addElement(date);
        lmLaw.addElement(law);
        return;
    }

    private class BuildButtonListener implements ActionListener {
        private JFrame frame;

        BuildButtonListener(JFrame frame) {
            this.frame = frame;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                int size = Integer.valueOf(tfSize.getText());
                elmCount = base.read(0,size);
                curIndex = 0;

                clearList();
                printList(20);
                JOptionPane.showMessageDialog(frame,"Построен список из "+elmCount+" элементов");
            }
            catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame,"Возникла ошибка!\n"+nfe.getMessage());
            }
            btnFind.setEnabled(true);
            tfFind.setEnabled(true);
            btnFindTree.setEnabled(true);
            tfFindTree.setEnabled(true);
        }
    }

    private class SortButtonListener implements ActionListener {
        final static int deposit = 0;
        final static int date = 1;
        int typeSort;
        SortButtonListener(int typeSort) {
            this.typeSort = typeSort;
        }
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            if (typeSort == 1) {
                elmCount = base.sortDate();
                curIndex = 0;
                clearList();
                printList(20);
            } else {
                elmCount = base.sortDep();
                curIndex = 0;
                clearList();
                printList(20);
            }
        }
    }

    private class GetSourceButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            elmCount = base.getSource();
            curIndex = 0;
            clearList();
            printList(20);
        }
    }

    private class CompressButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            FCoding fc = new FCoding();
            ArrayList<Boolean> bitlist = fc.getFromFile("testBase3.dat");
            byte[] data = fc.getArray(bitlist);
            Utils.writeDataTo("compressed.dat", data);
            new CompressedForm(bitlist, fc.getLetters(), fc.getFactor());
        }
    }

    private class FindTreeButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                int key = Integer.valueOf(tfFindTree.getText());
                curIndex = 0;
                clearList();
                elmCount = base.searchByTree(key);
                printList(20);
            }
            catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null,"Возникла ошибка!\n"+nfe.getMessage());
            }
        }
    }

    private class FindButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            try {
                int key = Integer.valueOf(tfFind.getText());
                curIndex = 0;
                clearList();
                elmCount = base.search(key);
                printList(20);
            }
            catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null,"Возникла ошибка!\n"+nfe.getMessage());
            }
        }
    }

    private class NavigationButtonListener implements ActionListener {
        final static int left = 0;
        final static int right = 1;
        private JFrame frame;
        private int dir;

        NavigationButtonListener(JFrame frame, int dir) {
            this.frame = frame;
            this.dir = dir;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            clearList();
            try {
                if (dir == 1) {
                    printList(20);
                } else {
                    printList(-20);
                }
            } catch(NullPointerException npe) {
                JOptionPane.showMessageDialog(frame,"Возникла ошибка!\n"+npe.getMessage());
            } catch (ArrayIndexOutOfBoundsException ai) {
                JOptionPane.showMessageDialog(frame,"Проблема!\n"+"Текущий элемент: "+ai.getMessage());
            }
            return;
        }
    }
}
