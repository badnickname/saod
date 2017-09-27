package ru.ress.coursework;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by ress on 26.09.17.
 */
public class Base {
    private String filename;
    private int lpos, rpos;
    private byte elmSize;
    private Data[] base;

    public Base(String name) {
        filename = name;
        elmSize = 64;
        rpos = lpos = 0;
    }

    public String getName(int i) {
        return new String(base[i].name);
    }

    public String getDate(int i) {
        return new String(base[i].date);
    }

    public String getLawyer(int i) {
        return new String(base[i].lawyer);
    }

    public int getDeposit(int i) {
        return base[i].deposit;
    }

    public void read(int pos, int len) {
        try (FileInputStream fin = new FileInputStream(filename)) {

            countPos(pos, len, fin.available());
            int byteLen = rpos-lpos;
            base = new Data[byteLen/elmSize];

            fin.skip(lpos);
            byte[] buf = new byte[byteLen];
            fin.read(buf, 0, (byteLen));

            for (int curElement = 0; curElement<byteLen; curElement+=elmSize) {
                Data dat = new Data();
                base[(curElement)/elmSize] = dat;

                for(int cByte=0; cByte<30; cByte++) {
                    dat.name[cByte] = Crutch.toRussian( buf[cByte+curElement] );
                }

                dat.deposit = Crutch.bytesToInt( buf[30+curElement], buf[31+curElement] );

                for(int cByte=32; cByte<42; cByte++) {
                    dat.date[cByte-32] = Crutch.toRussian( buf[cByte+curElement] );
                }

                for(int cByte=42; cByte<64; cByte++) {
                    dat.lawyer[cByte-42] = Crutch.toRussian( buf[cByte+curElement] );
                }
            }

            fin.close();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return;
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
