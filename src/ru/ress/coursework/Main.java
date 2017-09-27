package ru.ress.coursework;

/**
 * Created by ress on 26.09.17.
 */
public class Main {
    public static void main(String args[]) {
        Base base = new Base("testBase3.dat");
        base.read(0,20);
        for(int i=0; i<20; i++) {
            System.out.printf("%3d) %30s %15d %10s %22s\n", i+1, base.getName(i), base.getDeposit(i),
                    base.getDate(i), base.getLawyer(i));
        }
        return;
    }
}
