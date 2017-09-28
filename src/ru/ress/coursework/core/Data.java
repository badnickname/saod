package ru.ress.coursework.core;

/**
 * Created by ress on 26.09.17.
 */
class Data {
    char[] name; //30
    int deposit; //unsigned short int
    char[] date; //10
    char[] lawyer; //22

    Data() {
        name = new char[30];
        date = new char[10];
        lawyer = new char[22];
    }
}
