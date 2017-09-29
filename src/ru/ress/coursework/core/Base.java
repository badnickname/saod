package ru.ress.coursework.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ress on 26.09.17.
 */
public class Base {
    private String filename;
    private int lpos, rpos;
    private byte elmSize;
    private ArrayList<Data> outputBase;
    private Data sourceList, headList;

    public Base(String name) {
        filename = name;
        elmSize = 64;
        rpos = lpos = 0;
    }

    public String getName(int i) {
        return Crutch.getString(outputBase.get(i).name);
    }

    public String getDate(int i) {
        return Crutch.getString(outputBase.get(i).date);
    }

    public String getLawyer(int i) {
        return Crutch.getString(outputBase.get(i).lawyer);
    }

    public int getDeposit(int i) {
        return outputBase.get(i).deposit;
    }

    public int read(int pos, int len) {
        int byteLen = 0;
        try (FileInputStream fin = new FileInputStream(filename)) {

            countPos(pos, len, fin.available());
            byteLen = rpos-lpos;

            fin.skip(lpos);
            byte[] buf = new byte[byteLen];
            fin.read(buf, 0, (byteLen));

            Data head = sourceList = headList = null;

            for (int curElement = 0; curElement<byteLen; curElement+=elmSize) {
                Data dat = new Data();
                if (head == null) {
                    head = sourceList = dat;
                } else {
                    head.next = dat;
                    head = dat;
                }

                for(int cByte=0; cByte<30; cByte++) {
                    dat.name[cByte] = buf[cByte+curElement];
                }

                dat.deposit = Crutch.bytesToInt( buf[30+curElement], buf[31+curElement] );

                for(int cByte=32; cByte<42; cByte++) {
                    dat.date[cByte-32] = buf[cByte+curElement];
                }

                for(int cByte=42; cByte<64; cByte++) {
                    dat.lawyer[cByte-42] = buf[cByte+curElement];
                }
            }

            fin.close();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        getOutput(sourceList);
        return byteLen/elmSize;
    }

    public int sortDate() {
        getOutput(headList);
        sourceList = (new DigitalSort(sourceList)).sort(DigitalSort.date);
        getOutput(sourceList);
        return outputBase.size();
    }

    public int sortDep() {
        getOutput(headList);
        sourceList = (new DigitalSort(sourceList)).sort(DigitalSort.deposit);
        getOutput(sourceList);
        return outputBase.size();
    }

    public int search(int key) {
        sortDep();
        outputBase = BinarySearch.find(key,outputBase);
        if (outputBase == null) return 0;
        return outputBase.size();
    }

    private void getOutput(Data pointer) {
        outputBase = new ArrayList<Data>();
        Data p = pointer;
        while (p != null) {
            outputBase.add(p);
            p = p.next;
        }
    }

    private void countPos(int pos, int len, int allow) {
        if (pos*elmSize > allow) {
            lpos = 0;
        } else {
            lpos = pos;
        }

        rpos = (lpos+len)*elmSize;
        lpos *= elmSize;

        if (rpos > allow) {
            rpos = allow;
        }

        return;
    }

}
