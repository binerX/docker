package com.bin.yang.rest.api;

import java.util.ArrayList;

/**
 * @ClassName: com.bin.yang.rest.api.BTree
 * @Author: bin.yang
 * @Date: 2021/1/4 11:41
 * @Description: TODO B树
 */
public class BTree<Key extends Comparable, Value>  {

    public static final int MAX_CHILD = 4;

    private int size;

    private int height;

    private BNode root;

    public BTree(){

        this.root = new BNode(true);
    }

    public void add(Key key,Value  value){
        BNode t = insert(root,key,value);
        if(t == null){
            return;
        }
        this.root = t;
    }

    private BNode insert(BNode node, Key key, Value value) {
        int i;
        BNode insert = null;
        Entry entry = new Entry(key, value);
        ArrayList<Entry> entrys = node.entrys;
        if(node.leaf){
            for (i = 0; i < entrys.size(); i++) {
                if(equals(key,entrys.get(i).Key)){
                    return null;
                }
                if(less(key,entrys.get(i).Key)){
                    break;
                }
            }
        }
        else
        {
            for (i = 0; i < entrys.size(); i++) {
                if(equals(key,entrys.get(i).Key)){
                    return null;
                }
                if(less(key,entrys.get(i).Key)){
                    break;
                }
            }
            insert = insert(node.children.get(i), key, value);
            if(insert == null){
               return null;
            }
            entry = insert.entrys.get(0);
        }

        entrys.add(i,entry);
        if(insert != null){
            ArrayList<BNode> children = node.children;
            ArrayList<BNode> bNodes = new ArrayList<>();
            for (int j = 0; j < children.size(); j++) {
                if(i == j){
                    bNodes.add(insert.children.get(0))  ;
                    bNodes.add(insert.children.get(1))  ;
                }else {
                    bNodes.add(children.get(j));
                }
            }
            node.children  = bNodes;
        }
        size++;
        if(entrys.size() == MAX_CHILD){
            return split(node);
        }else {
           return null ;
        }
    }

    private BNode split(BNode node) {
        ArrayList<Entry> entrys = node.entrys;
        ArrayList<BNode> children = node.children;
        int i = MAX_CHILD / 2 -1;
        Entry entry = entrys.get(i);
        ArrayList<Entry> top = new ArrayList<>();
        ArrayList<Entry> l = new ArrayList<>();
        ArrayList<Entry> r = new ArrayList<>();
        top.add(entry);
        l.add(entrys.get(0));
        r.add(entrys.get(2));
        r.add(entrys.get(3));
        BNode bNodeL = new BNode(true);
        bNodeL.entrys = l;
        BNode bNodeR = new BNode(true);
        bNodeR.entrys = r;
        if(children != null && children.size() > 0){
            ArrayList<BNode> bNodel = new ArrayList<>();
            bNodel.add(children.get(0));
            bNodel.add(children.get(1));
            ArrayList<BNode> bNoder = new ArrayList<>();
            bNoder.add(children.get(2));
            bNoder.add(children.get(3));
            bNoder.add(children.get(4));
            bNodeL.children = bNodel;
            bNodeL.leaf = false;
            bNodeR.children = bNoder;
            bNodeR.leaf = false;
        }
        ArrayList<BNode> bNodes = new ArrayList<>();
        bNodes.add(bNodeL);
        bNodes.add(bNodeR);
        node.entrys = top;
        node.children = bNodes;
        node.leaf=false;
        return node;
    }

    private boolean equals(Key key, Comparable key1) {
        return key.compareTo(key1) == 0;
    }
    private boolean less(Key key, Comparable key1) {
        return key.compareTo(key1) == -1;
    }


//    public static void main(String[] args) {
//
//        BTree<String,Object> b = new BTree<>();
//
//        b.add("a",64545);
//        b.add("b",453);
//        b.add("c",9876);
//        b.add("d",'y');
//        b.add("e","this");
//        b.add("f","的给第三方");
//        b.add("g",35.63);
//        b.add("h",true);
//        b.add("i",false);
//        b.add("j",23.545);
//        b.add("k","第三方");
//        b.add("l","很关键");
//        b.add("m","低功耗");
//        b.add("n",42);
//
////        b.add("o",53.2);
////        b.add("p",13.79);
////        b.add("q",'A');
//
//        b.add("g1",23.545);
//        b.add("g2","第三方");
//        b.add("g3","很关键");
//        b.add("g4","低功耗");
//        b.add("g5",42);
//        b.add("g6",42);
//        b.add("g7",42);
//
//        System.out.println();
//    }



}


class BNode{

    public ArrayList<Entry>  entrys;

    public ArrayList<BNode>  children;

    // 是否为叶子节点
    public boolean leaf;

    public BNode(boolean b){
        this.entrys = new ArrayList<>();
        this.children = new ArrayList<>();
        this.leaf = b;
    }
}
class Entry{

    public Comparable Key;

    public Object Value;


    public Entry(Comparable k, Object v){
        this.Key = k;
        this.Value = v;
    }

    @Override
    public String toString() {
        return Key.toString() + ":" + Value.toString();
    }
}

