package ru.ress.coursework;

import ru.ress.coursework.core.Base;
import ru.ress.coursework.entropy.Entropy;
import ru.ress.coursework.gui.MainForm;

/**
 * Created by ress on 26.09.17.
 */
public class Main {
    public static void main(String args[]) {
        new MainForm("Base 3", new Base("testBase3.dat"));
        Entropy ent = new Entropy();
        //System.out.println(ent.getEntropyFile("testBase3.dat"));
        //System.out.println(ent.getEntropyFile("compressed.dat"));
    }
}
