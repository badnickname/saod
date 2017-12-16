package ru.ress.coursework.core;

/**
 * Created by ress on 26.09.17.
 */
public class Data {
    Data next;
    public byte[] name; //30
    public int deposit; //unsigned short int
    public byte[] date; //10
    public byte[] lawyer; //22

    Data() {
        name = new byte[30];
        date = new byte[10];
        lawyer = new byte[22];
    }
}
