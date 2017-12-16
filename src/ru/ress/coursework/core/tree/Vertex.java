package ru.ress.coursework.core.tree;

import ru.ress.coursework.core.Data;

public class Vertex {
    public Vertex l, r;
    public int w;
    public Data data;
    int h;

    public Vertex(int w, Data data) {
        this.w = w;
        this.data = data;
        l = null;
        r = null;
        h = 0;
    }

    public Vertex(Data data) {
        this(0, data);
    }

    public int getData() {
        return data.deposit;
    }
}
