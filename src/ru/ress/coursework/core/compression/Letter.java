package ru.ress.coursework.core.compression;

import java.util.ArrayList;

public class Letter {
    public double probability;
    public ArrayList<Boolean> code;
    public byte ch;

    public int getLength() {
        return code.size();
    }

    Letter(byte ch) {
        this.ch = ch;
        probability = 0;
        code = new ArrayList<>();
        for(boolean elm: code) {
            elm = false;
        }
    }
}
