package ru.ress.coursework.gui;

import ru.ress.coursework.core.Base;

import javax.swing.*;
import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ress on 28.09.17.
 */
public class MainForm extends JFrame {
    private DefaultListModel lmname, lmdep, lmdate, lmlaw, lmnumb;
    private JList lname, ldep, ldate, llaw, lnumb;
    private JRadioButton rname, rdep, rdate, rlaw;
    private JTextField tsize, tfind;
    private JButton bsize, bfind, bleft, bright;
    private Base base;

    private int elmCount;
    private int curIndex;

    public MainForm(String title, Base base) throws HeadlessException {
        super(title);
        this.base = base;

        Container cont = this.getContentPane();
        cont.setLayout( new GridLayout(1,2 ));

        JPanel lpanel = new JPanel();
        JPanel botpanel = new JPanel( new GridLayout(2,1));

        JPanel buildpanel = new JPanel();
        JPanel findpanel = new JPanel();

        rname = new JRadioButton("Имя");
        rdep = new JRadioButton("Депозит");
        rdate = new JRadioButton("Дата");
        rlaw = new JRadioButton("Адвокат");

        bleft = new JButton("<<");
        bleft.addActionListener(new NavigationButtonListener(this, NavigationButtonListener.left));
        bright = new JButton(">>");
        bright.addActionListener(new NavigationButtonListener(this, NavigationButtonListener.right));

        tsize = new JTextField("20",10);
        bsize = new JButton("Загрузить");
        bsize.addActionListener(new BuildButtonListener(this));

        tfind = new JTextField("",10);
        bfind = new JButton("Найти");

        lmname = new DefaultListModel();
        lmdep = new DefaultListModel();
        lmdate = new DefaultListModel();
        lmlaw = new DefaultListModel();
        lmnumb = new DefaultListModel();

        lname = new JList(lmname);
        ldep = new JList(lmdep);
        ldate = new JList(lmdate);
        llaw = new JList(lmlaw);
        lnumb = new JList(lmnumb);

        lpanel.add(lnumb);
        lpanel.add(lname);
        lpanel.add(ldep);
        lpanel.add(ldate);
        lpanel.add(llaw);

        buildpanel.add(new JLabel("Размер: "));
        buildpanel.add(tsize);
        buildpanel.add(bsize);
        buildpanel.add(bleft);
        buildpanel.add(bright);

        findpanel.add(rname);
        findpanel.add(rdep);
        findpanel.add(rdate);
        findpanel.add(rlaw);
        findpanel.add(tfind);
        findpanel.add(bfind);

        botpanel.add(buildpanel);
        botpanel.add(findpanel);

        cont.add(lpanel);
        cont.add(new JScrollPane(lpanel));
        cont.add(botpanel);

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
        lmdep.clear();
        lmdate.clear();
        lmlaw.clear();
        lmnumb.clear();
        lmname.clear();
        return;
    }

    private void addElement(int numb, String name, int deposit, String date, String law) {
        lmnumb.addElement(numb);
        lmname.addElement(name);
        lmdep.addElement(deposit);
        lmdate.addElement(date);
        lmlaw.addElement(law);
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
                int size = Integer.valueOf(tsize.getText());
                elmCount = base.read(0,size);
                curIndex = 0;

                clearList();
                printList(20);
                JOptionPane.showMessageDialog(frame,"Построен список из "+elmCount+" элементов");
            }
            catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(frame,"Возникла ошибка!\n"+nfe.getMessage());
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
