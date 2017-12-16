package ru.ress.coursework.core.compression;

import java.util.ArrayList;

public class FCoding {
    private Letter[] letters;
    private int sourceSize;
    private int resultSize;

    public ArrayList<Boolean> getFromFile(String filename) {
        byte[] source = Utils.getDataFrom(filename);
        sourceSize = source.length;
        letters = Utils.getProbalities(source);
        letters = Utils.sort(letters);
        codingFano(0, letters.length - 1);
        ArrayList<Boolean> bitlist = new ArrayList<>();
        for (int i = 0; i < source.length; i++) {
            for (int j = 0; j < letters.length; j++) {
                if (source[i] == letters[j].ch) {
                    bitlist.addAll(letters[j].code);
                    break;
                }
            }
        }
        return bitlist;
    }

    public double getFactor() {
        return (double)sourceSize/resultSize;
    }

    public Letter[] getLetters() {
        return letters;
    }

    public byte[] getArray(ArrayList<Boolean> bitlist) {
        ArrayList<java.lang.Byte> bytelist = new ArrayList<>();
        int pos;
        for (pos = 7; pos < bitlist.size(); pos += 8) {
            boolean[] value = new boolean[8];
            for (int i = 0; i < 8; i++) {
                value[i] = bitlist.get(pos - 7);
            }
            Byte _byte = new Byte(value);
            bytelist.add(_byte.getValue());
        }
        byte[] array = new byte[bytelist.size()];
        for(int i=0; i<bytelist.size(); i++) {
            array[i] = bytelist.get(i);
        }
        resultSize = array.length;
        return array;
    }

    private int med(int l, int r) {
        double sumLeft = 0;
        for(int i=l; i<r; i++) {
            sumLeft += letters[i].probability;
        }
        double sumRight = letters[r].probability;
        int m = r;
        while(sumLeft >= sumRight) {
            m-=1;
            sumLeft -= letters[m].probability;
            sumRight += letters[m].probability;
        }
        return m;
    }

    private void codingFano(int l, int r) {
        if(l<r) {
            int m = med(l,r);
            for(int i=l; i<=r; i++) {
                if(i<=m) {
                    letters[i].code.add(false);
                } else {
                    letters[i].code.add(true);
                }
            }
            codingFano(l, m);
            codingFano(m+1, r);
        }
    }

}
