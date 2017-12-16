package ru.ress.coursework.core.tree;

public class Tree {
    private String outStr;
    private Vertex root;

    public Vertex getRoot() {
        //  return tree
        return root;
    }

    protected void setRoot(Vertex vert) {
        //  set new root
        root = vert;
    }

    public void makeTree(int size) {
        //  get size of tree
    }

    private void printVertex(Vertex p) {
        //  get Vertex, string to output
        //  print Vertex's data and call recursion for left and right subtrees
        if(p == null) return;
        printVertex(p.l);
        // String vertexStr = "data: " + Integer.toString(p.d) + " param: " + Integer.toString(p.w);
        // outStr += vertexStr + "\n";
        printVertex(p.r);
    }

    public String printTree(String out) {
        //  get string to output
        //  return tree str
        outStr = "";
        printVertex(root);
        return out+outStr;
    }

    private int vertexHeight(Vertex vert) {
        //  get vertex of tree
        //  return height current subtree
        if(vert == null) return 0;
        int hLeft = vertexHeight(vert.l);
        int hRight = vertexHeight(vert.r);
        if (hLeft>hRight) vert.h = ++hLeft; else vert.h = ++hRight;
        return vert.h;
    }

    public int getHeight() {
        //  return height of tree
        return vertexHeight(root);
    }

    private int vertexPath(Vertex vert, int l) {
        //  get: root of sub tree, path
        //  return path to vertex from root of tree
        int cP = 0;
        if (vert != null) {
            cP = l + vertexPath(vert.l, l+1) + vertexPath(vert.r, l+1);
        }
        return cP;
    }

    public double getAvgHeight() {
        //  return height of tree
        return (double)vertexPath(root, 0)/vertexSize(root);
    }

    private int vertexSize(Vertex vert) {
        //  return size of vertex
        if(vert == null) return 0;
        return vertexSize(vert.l) + vertexSize(vert.r) + 1;
    }

    public int getSize() {
        //  return size of tree
        return vertexSize(root);
    }

    private int vertexSumm(Vertex vert) {
        //  return summ of vertex
        if(vert == null) return 0;
        return vertexSumm(vert.l) + vertexSumm(vert.r) + vert.getData();
    }

    public int getSumm() {
        //  return summ of tree
        return vertexSumm(root);
    }
}
