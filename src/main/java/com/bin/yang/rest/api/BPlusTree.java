package com.bin.yang.rest.api;

/**
 * @ClassName: com.bin.yang.rest.api.BPlusTree
 * @Author: bin.yang
 * @Date: 2021/1/5 16:32
 * @Description: TODO  B+Tree b+树
 */
public class BPlusTree<K extends Comparable, V> {

    public static final int M = 4;

    private int H;

    private int N;

    private BPNode root;

    private BPEntry next;

    public BPlusTree(){
        this.root = new BPNode(true);
    }


    public void add(K k, V v){
        BPNode t = insert(root, k, v);
        if(t == null){
            return;
        }
        this.root = t;
        H++;
    }

    private BPNode insert(BPNode node, K k, V v) {

        BPEntry entry = new BPEntry(k,v);
        if(node.leaf){
            int I = node.exist(k);
            if(I == -1){
                System.out.println("元素已存在");
                return null;
            }
            node.add(entry,k,I);
            node.n++;
            if(this.next == null || k.compareTo(this.next.key) == -1){
                this.next = entry;
            }
            N++;
        }else {
            int I = node.getIndex(k);
            if(I == -1){
                System.out.println("元素已存在");
                return null;
            }
            BPNode insert = insert(node.children[I], k, v);
            if(insert == null){
               return null;
            }
            addNode(node, insert, I);
            node.n++;
        }
        if(node.n > M){
            return split(node);
        }else {
            return null;
        }
    }

    private void addNode(BPNode node, BPNode insert, int i) {
        for (int j = node.n -1; j > i; j--) {
            node.keys[j +1] = node.keys[j];
            node.children[j +1] = node.children[j];
        }
        node.keys[i] = insert.keys[0];
        node.keys[i+1] = insert.keys[1];
        node.children[i] = insert.children[0];
        node.children[i+1] = insert.children[1];
    }

    private BPNode split(BPNode node) {
        BPNode l = new BPNode();
        BPNode r = new BPNode();
        if(node.leaf == true){
            l.leaf = true;
            r.leaf = true;
        }
        l.keys[0] = node.keys[0];
        l.keys[1] = node.keys[1];
        r.keys[0] = node.keys[2];
        r.keys[1] = node.keys[3];
        r.keys[2] = node.keys[4];
        l.entrys[0] = node.entrys[0];
        l.entrys[1] = node.entrys[1];
        r.entrys[0] = node.entrys[2];
        r.entrys[1] = node.entrys[3];
        r.entrys[2] = node.entrys[4];
        if(node.leaf == false){
            l.children[0] =  node.children[0];
            l.children[1] =  node.children[1];
            r.children[0] =  node.children[2];
            r.children[1] =  node.children[3];
            r.children[2] =  node.children[4];
        }
        BPNode[] nodes = new BPNode[M + 1];
        l.n = 2;
        r.n = 3;
        nodes[0] = l;
        nodes[1] = r;
        BPNode top = new BPNode();
        top.children = nodes;
        top.n = 2;
        top.keys[0] = l.keys[0];
        top.keys[1] = r.keys[0];
        return top;
    }

    /*public static void main(String[] args) {

        BPlusTree tree = new BPlusTree();

        tree.add(6,8);
        tree.add(66,65);
        tree.add(99,876);
        tree.add(33,"trwe");
        tree.add(55,"nnbv");
        tree.add(77,"nnbv");
        tree.add(88,"nnbv");
        tree.add(3,654);
        tree.add(474,654);
        tree.add(2,654);
        tree.add(1,654);
        tree.add(111,111);
        System.out.println();
    }
*/

}

class BPNode<K extends Comparable> {

    public Comparable[] keys;

    public BPNode[] children;

    public BPEntry[] entrys;

    public boolean leaf;

    public int n;

    public BPNode(){
        this.keys = new Comparable[BPlusTree.M+1];
        this.entrys = new BPEntry[BPlusTree.M+1];
        this.children = new BPNode[BPlusTree.M+1];
        this.leaf = false;
    }

    public BPNode(boolean b){
        this.keys = new Comparable[BPlusTree.M+1];
        this.entrys = new BPEntry[BPlusTree.M+1];
        this.children = new BPNode[BPlusTree.M+1];
        this.leaf = b;
    }

    public int exist(K k){
        for (int i = n ; i > 0; i--) {
            if(keys[i -1].compareTo(k) == 0){
                return -1;
            }
            if (k.compareTo(keys[i -1]) == 1){
                return i;
            }
        }
        return 0;
    }

    public void add(BPEntry entry, K k, int i){
        for (int j = n; j >=i ; j--) {
            if(j == i){
                break;
            }
            keys[j] = keys[j -1];
            entrys[j] = entrys[j -1];
        }
        keys[i] = k;
        entrys[i] = entry;

        if(i != 0){
            entry.next = entrys[i-1].next;
            entrys[i-1].next = entry;
        }else {
            entry.next = entrys[i+1];
        }
    }

    public int getIndex(K k) {
        for (int i = n -1; i >= 0; i--) {
            if(k.compareTo(keys[i]) == 0){
                return -1;
            }
            if (k.compareTo(keys[i]) == 1){
                return i;
            }
        }
        return 0;
    }
}

class BPEntry<K extends Comparable, V>{

    public K key;

    public V value;

    public BPEntry next;

    @Override
    public String toString() {
        return "Entry[ " + key.toString() + ":" + value.toString() + " ]";
    }

    public BPEntry(K k, V v){
        this.key = k;
        this.value = v;
    }
}