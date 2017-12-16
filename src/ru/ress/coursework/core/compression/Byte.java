package ru.ress.coursework.core.compression;

public class Byte {
    private boolean data[];

    public Byte() {
        data = new boolean[8];
        for(boolean elm : data) {
            elm = false;
        }
    }

    public Byte(boolean[] value) {
        this();
        setByte(value);
    }

    public void setByte(boolean[] value) {
        System.arraycopy(value, 0, data, 0, 8);
    }

    private byte getNumber(boolean value) {
        if(value) return 1; else return 0;
    }

    public byte getValue() {
        byte value = 0;
        for(int i = 7; i>=0; i--) {
            value += getNumber(data[i]) * (byte)Math.pow(2,7-i);
        }
        return value;
    }
}
