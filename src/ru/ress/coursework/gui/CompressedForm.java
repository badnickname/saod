package ru.ress.coursework.gui;

import ru.ress.coursework.core.compression.Letter;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CompressedForm extends JDialog {
    private JPanel contentPane;
    private JEditorPane editorPane1;
    private JLabel jLabel1;
    private JLabel jLabel2;

    public CompressedForm(ArrayList<Boolean> data, Letter[] letters, double factor) {
        setContentPane(contentPane);
        setModal(true);

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        String str = "";
        for(int i=0; i<10000; i++) {
            if(i>=data.size()) break;
            byte value = 0;
            if(data.get(i)) {
                value = 1;
            }
            str += Byte.toString(value);
        }
        editorPane1.setText(str);

        double size = 0;
        for(Letter elm : letters) {
            size += elm.getLength() * elm.probability;
            System.out.printf(";\n ELEMENT: %d === CODE: ", elm.ch);
            for(boolean num : elm.code) {
                if(num) System.out.print("1"); else System.out.print("0");
            }
        }
        jLabel1.setText(Double.toString(size));
        jLabel2.setText(Double.toString(factor));
        pack();
        setVisible(true);
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
