package ru.ress.coursework.core;

/**
 * Created by ress on 26.09.17.
 */
class Data {
    Data next;
    byte[] name; //30
    int deposit; //unsigned short int
    byte[] date; //10
    byte[] lawyer; //22

    Data() {
        name = new byte[30];
        date = new byte[10];
        lawyer = new byte[22];
    }
}
