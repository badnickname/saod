package ru.ress.coursework.core.compression;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Utils {

    static public byte[] getDataFrom(String filename) {
        byte[] data = new byte[0];
        try {
            FileInputStream fin = new FileInputStream(filename);
            data = new byte[fin.available()];
            fin.read(data, 0, fin.available());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    static public void writeDataTo(String filename, byte[] array) {
        try {
            FileOutputStream fos = new FileOutputStream(filename);
            fos.write(array, 0, array.length);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public Letter[] getProbalities(byte[] array) {
        Letter[] prob = new Letter[255];
        for(byte i=-128; i<127; i++) {
            prob[i+128] = new Letter(i);
        }
        for(byte elm : array) {
            prob[elm+128].probability += 1;
        }
        ArrayList<Letter> list = new ArrayList<>();
        for(Letter elm : prob) {
            if(elm.probability > 0) list.add(elm);
        }
        prob = new Letter[list.size()];
        list.toArray(prob);
        double h = 0;
        for(Letter let : prob) {
            let.probability = let.probability/array.length;
            h -= ( let.probability * log2(let.probability) );
        }
        System.out.printf("H = %f", h);
        return prob;
    }

    private static double log2(double n) {
        return (Math.log(n) / Math.log(2));
    }

    static public Letter[] sort(Letter[] array) {
        for(int j=1; j<array.length; j++) {
            Letter key = array[j];
            int i = j-1;
            while(i>=0 && array[i].probability<key.probability) {
                array[i+1] = array[i];
                i -= 1;
            }
            array[i+1] = key;
        }
        return array;
    }

}
