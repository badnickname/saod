package ru.ress.coursework.entropy;

import ru.ress.coursework.core.compression.Utils;

import java.util.ArrayList;

public class Entropy {
    private ArrayList<Boolean> getBitsFromByte(byte bt) {
        ArrayList<Boolean> _bits = new ArrayList<>();
        int _byte = bt+128;
        while(_byte>0) {
            int mod = _byte%2;
            _byte /= 2;
            if(mod == 0) _bits.add(0, false); else _bits.add(0, true);
        }
        return _bits;
    }

    private double log2(double n) {
        return (Math.log(n) / Math.log(2));
    }

    public double getEntropyFile(String filename) {
        byte[] file = Utils.getDataFrom(filename);
        ArrayList<Boolean> bits = new ArrayList<>();
        for(int i=0; i<file.length; i++) {
            bits.addAll(getBitsFromByte(file[i]));
        }
        int[] let = new int[2];
        let[0] = let[1] = 0;
        for(boolean elm: bits) {
            if(elm) let[1] += 1; else let[0] += 1;
        }
        double[] p = new double[2];
        p[0] = (double)let[0] / (let[1]+let[0]);
        p[1] = (double)let[1] / (let[1]+let[0]);
        return -p[0]*log2(p[0]) - p[1]*log2(p[1]);
    }

}
