package ru.ress.coursework;

import ru.ress.coursework.core.Base;
import ru.ress.coursework.gui.MainForm;

/**
 * Created by ress on 26.09.17.
 */
public class Main {
    public static void main(String args[]) {
        Base base = new Base("testBase3.dat");
        MainForm form = new MainForm("Base 3", base);

        /*base.read(0,40);
        form.clearList();
        for(int i=0; i<40; i++) {
            form.addElement(i, base.getName(i), base.getDeposit(i), base.getDate(i), base.getLawyer(i));
        }*/

        return;
    }
}
