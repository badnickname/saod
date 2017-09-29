package ru.ress.coursework.core;

import java.util.ArrayList;

/**
 * Created by ress on 29.09.17.
 */
class DigitalSort {
    final static boolean date = true;
    final static boolean deposit = false;
    final static int nill = 0;
    private Data head;
    private Queue[] q;
    private int sizeofbyte;


    DigitalSort(Data pointer) {
        sizeofbyte = 256;
        head = pointer;
        q = new Queue[sizeofbyte];
    }

    public Data sort(boolean type) {
        int len;
        if (type == date) len = 10; else len = 2;

        for(int j=len-1; j>=0; j--) {

            for(int i = 0; i<sizeofbyte; i++) {
                q[i] = new Queue();
            }
            Data p = head;

            do {
                int d = calcQueue(type,p,j);
                if (q[d].head == null) {
                    q[d].head = q[d].tail = p;
                } else {
                    q[d].head.next = p;
                    q[d].head = p;
                }
                p = p.next;
            } while (p != null);

            for(int i=0;i<sizeofbyte;i++) {
                if (q[i].tail != null) {
                    if (p==null) {
                        head = q[i].tail;
                        p = q[i].head;
                        continue;
                    }
                    p.next = q[i].tail;
                    p = q[i].head;
                    p.next = null;
                }
            }
        }

        return head;
    }

    private int calcQueue(boolean type, Data p, int j) {
        if (type == date) {
            return p.date[j] + 128;
        }

        int d;
        switch (j) {
            case 1: {
                d = p.deposit & 0xff;
                break;
            }
            default: {
                d = (p.deposit & 0xff00) >> 8;
                break;
            }
        }
        return d;
    }

    private class Queue {
        Data tail, head;
        Queue() {
            head = tail = null;
        }
    }

}
