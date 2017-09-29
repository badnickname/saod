package ru.ress.coursework.core;

import java.util.ArrayList;

/**
 * Created by ress on 29.09.17.
 */
class BinarySearch {
    static ArrayList<Data> find(int key, ArrayList<Data> arr) {
        int l = 0;
        int r = arr.size();
        while (l<r) {
            int m =(l+r)/2;
            if (arr.get(m).deposit < key) l = m+1;
            else r = m;
        }
        if (arr.get(r).deposit != key) return null;

        Data p = arr.get(r);
        ArrayList<Data> out = new ArrayList<Data>();
        while (p!=null && p.deposit==key) {
            out.add(p);
            p = p.next;
        }
        return out;
    }
}
