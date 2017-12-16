package ru.ress.coursework.core.tree.btree;

import ru.ress.coursework.core.tree.Tree;
import ru.ress.coursework.core.tree.Vertex;
import ru.ress.coursework.core.Data;

import java.util.ArrayList;
import java.util.Random;

public class BTree extends Tree {
    private Vertex root;
    private boolean hgrow = true;
    private boolean vgrow = true;

    public Data search(int key) {
        Vertex p = root;
        while(p != null) {
            if(key < p.getData()) {
                p = p.l;
            } else {
                if(key > p.getData()) {
                    p = p.r;
                } else {
                    break;
                }
            }
        }
        if(p!=null) return p.data; else return null;
    }

    public void makeTreeFromArray(ArrayList<Data> data) {
        setRoot(generateTreeFromArray(data));
    }

    private Vertex generateTreeFromArray(ArrayList<Data> data) {
        root = null;
        for(Data elm : data) {
            addVertex(elm, new Vector(null, Vector.left));
        }
        return root;
    }

    private class Vector {
        final static boolean left = false;
        final static boolean right = true;
        private Vertex prev = null;
        private boolean dir = false;

        Vector(Vertex prev, boolean dir) {
            //  set vertex's link, direction (left/right) to next vertex
            this.prev = prev;
            this.dir = dir;
        }

        void set(Vertex vert) {
            //  set vertex in direction
            if(prev == null) {
                root = vert;
                return;
            }
            if(dir) prev.r = vert; else prev.l = vert;
        }

        Vertex get() {
            //  get current vertex
            if(prev == null) return root;
            if(dir) return prev.r; else return prev.l;
        }
    }

    private void addVertex(Data data, Vector root) {
        //  get: data of adding vertex, vector pointing to the vertex
        //  recursively add new vertex
        if(root.get() == null) {
            root.set( new Vertex(data) );
            vgrow = true;
            return;
        }
        if(root.get().getData() > data.deposit) {
            addVertex(data, new Vector( root.get(), Vector.left ));
            if(vgrow) {
                if(root.get().w == 0) {
                    Vertex q = root.get().l;
                    root.get().l = q.r;
                    q.r = root.get();
                    root.set(q);
                    q.w = 1;
                    vgrow = false;
                    hgrow = true;
                } else {
                    root.get().w = 0;
                    vgrow = true;
                    hgrow = false;
                }
            } else {
                hgrow = false;
            }
        } else if(root.get().getData() < data.deposit) {
            addVertex(data, new Vector( root.get(), Vector.right ));
            if(vgrow) {
                root.get().w = 1;
                vgrow = false;
                hgrow = true;
            } else if(hgrow) {
                if(root.get().w > 0) {
                    Vertex q = root.get().r;
                    root.get().r = q.l;
                    root.get().w = 0;
                    q.w = 0;
                    q.l = root.get();
                    root.set(q);
                    vgrow = true;
                    hgrow = false;
                } else {
                    hgrow = false;
                }
            }
        }
    }

}
