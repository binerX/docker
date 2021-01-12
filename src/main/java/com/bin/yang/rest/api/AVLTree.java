package com.bin.yang.rest.api;

import java.util.Scanner;

/**
 * @ClassName: com.bin.yang.rest.api.AVLTree
 * @Author: bin.yang
 * @Date: 2020/12/29 11:42
 * @Description: TODO
 */
public class AVLTree {

    private AVLTreeNode treeNode;

    private int  size = 0;

    private int  depth = 0;

    static final int LEFT_MAX = -2;

    static final int RIGHT_MAX = 2;

    static final int BALANCE = 0;

    public void add (int val){
        if(treeNode == null){
            AVLTreeNode node = new AVLTreeNode();
            node.bf = 0;
            node.data = val;
            treeNode = node;
            size++;
            depth++;
            return;
        }
        boolean add = addVal(val);
        if (add) {
            findDepth(val);
        }
    }

    private void findDepth(int val) {
        AVLTreeNode temp = treeNode;
        int count = 1;
        for (;;){
            if(temp.data == val){
                break;
            }
            Comparable v = (Comparable) val;
            int to = v.compareTo(temp.data);
            if(to > 0){
                temp = temp.right;
            }else {
                temp = temp.left;
            }
            count++;
        }
        if(count > depth){
            depth = count;
        }
    }

    private boolean addVal(int val) {
        int bl;
        AVLTreeNode temp = treeNode;
        while (true){
            Comparable v = (Comparable) val;
            int to = v.compareTo(temp.data);
            if(to > BALANCE){
                if(temp.right == null){
                    AVLTreeNode node = new AVLTreeNode();
                    node.data  = val;
                    node.bf = 0;
                    node.parent = temp;
                    temp.right = node;
                    bl = temp.bf += 1;
                    size++;
                    break;
                }else {
                    temp.bf += 1;
                    temp = temp.right;
                }
            }else if(to < BALANCE ){
                if(temp.left == null){
                    AVLTreeNode node = new AVLTreeNode();
                    node.data  = val;
                    node.bf = 0;
                    node.parent = temp;
                    temp.left = node;
                    bl = temp.bf -= 1;
                    size++;
                    break;
                }else {
                    temp.bf -= 1;
                    temp = temp.left;
                }
            }else {
                System.out.println("元素已存在");
                return false;
            }
        }

        for(;;){
            temp = temp.parent;
            if(temp == null){
                return true;
            }
            if(bl == BALANCE){
                calibration(temp);
            }else {
                bl = rebuild(temp);
            }
        }
    }

    private int rebuild(AVLTreeNode temp) {
        if(temp.bf == RIGHT_MAX){
            leftBalance(temp);
        }
        if(temp.bf == LEFT_MAX){
            rightBalance(temp);
        }
        return temp.bf;
    }

    private void rightBalance(AVLTreeNode temp) {

        if(temp.left.bf < BALANCE ){
            rightRotate(temp);
        }else if(temp.left.bf > BALANCE){
            LeftRightRotate(temp);
        }
    }

    private void rightRotate(AVLTreeNode temp) {
        AVLTreeNode parent = temp.parent;
        AVLTreeNode top = temp.left;
        AVLTreeNode right = temp.left.right;
        top.bf = 0;
        temp.bf = 0;
        top.right = temp;
        temp.parent = top;
        temp.left = right;
        if(right != null){
            right.parent = temp;
        }
        top.parent = parent;
        if(parent == null){
            treeNode = top;
        }else {
            parent(parent,top);
        }
    }

    private void LeftRightRotate(AVLTreeNode temp) {
        AVLTreeNode parent = temp.parent;
        AVLTreeNode top = temp.left.right;
        AVLTreeNode left = temp.left;
        AVLTreeNode right = top.left;
        top.bf = 0;
        left.bf = 0;

        top.parent = temp;
        temp.left = top;
        top.left = left;
        left.parent = top;
        left.right = right;
        if(right != null){
            right.parent = left;
        }
        top = temp.left;
        right = temp.left.right;
        top.bf = 0;
        temp.bf = 0;
        top.right = temp;
        temp.parent = top;
        temp.left = right;
        if(right != null){
            right.parent = temp;
        }
        top.parent = parent;
        if(parent == null){
            treeNode = top;
        }else {
            parent(parent, top);
        }

    }

    private void leftBalance(AVLTreeNode temp) {
        if(temp.right.bf > BALANCE ){
            leftRotate(temp);
        }else if(temp.right.bf < BALANCE){
            rightLeftRotate(temp);
        }
    }

    private void leftRotate(AVLTreeNode temp) {
        AVLTreeNode parent = temp.parent;
        AVLTreeNode top = temp.right;
        AVLTreeNode left = temp.right.left;
        top.bf = 0;
        temp.bf = 0;
        top.left = temp;
        temp.parent = top;
        temp.right = left;
        if(left != null){
            left.parent = temp;
        }
        top.parent = parent;
        if(parent == null){
            treeNode = top;
        }else {
            parent(parent,top);
        }
    }

    private void rightLeftRotate(AVLTreeNode temp) {
        AVLTreeNode parent = temp.parent;
        AVLTreeNode top = temp.right.left;
        AVLTreeNode right = temp.right;
        AVLTreeNode left = top.right;
        top.bf = 0;
        right.bf = 0;
        top.parent = temp;
        temp.right = top;
        top.right = right;
        right.parent = top;
        right.left = left;
        if(left != null){
            left.parent = right;
        }
        top = temp.right;
        left = temp.right.left;
        top.bf = 0;
        temp.bf = 0;
        top.left = temp;
        temp.parent = top;
        temp.right = left;
        if(left != null){
            left.parent = temp;
        }
        top.parent = parent;
        if(parent == null){
            treeNode = top;
        }else {
            parent(parent,top);
        }
    }

    private void parent(AVLTreeNode parent, AVLTreeNode top) {
        Comparable v = (Comparable) parent.data;
        int to = v.compareTo(top.data);
        switch (to){
            case 1 :
                parent.left = top;
                break;
            case -1 :
                parent.right = top;
                break;
            default:
        }
    }

    private void calibration(AVLTreeNode parent){
        if(parent.bf > BALANCE){
            parent.bf -= 1;
        }else if(parent.bf < 0) {
            parent.bf += 1;
        }
    }

    public void list(){
        System.out.println();
        System.out.println();
        listp(treeNode,1);
//        listX(treeNode);
        System.out.println();
        System.out.println();
    }
    public void listX(){
        System.out.println();
        System.out.println();
        listX(treeNode);
        System.out.println();
        System.out.println();
    }
    private void listp(AVLTreeNode treeNode,int count) {
        if(treeNode != null){
            listp(treeNode.right, count +1);
            for (int i = 0; i < count; i++) {
                System.out.print("       ");
            }
            System.out.println(treeNode.data);
            listp(treeNode.left,count +1);

        }
    }

    private void listX(AVLTreeNode treeNode) {
        if(treeNode != null){
            System.out.println(treeNode.data);
            listX(treeNode.right);
            listX(treeNode.left);

        }
    }

//    public static void main(String[] args) {
//        AVLTree AVLtree = new AVLTree();
////        AVLtree.add(100);
////        AVLtree.add(50);
////        AVLtree.add(1);
////        AVLtree.add(80);
////        AVLtree.add(60); AVLtree.list();
////        AVLtree.add(243); AVLtree.list();
////        AVLtree.add(23); AVLtree.list();
////        AVLtree.add(63450); AVLtree.list();
////        AVLtree.add(6350); AVLtree.list();
////        AVLtree.add(11); AVLtree.list();
////        AVLtree.add(6500); AVLtree.list();
//
//
//
//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            for (int i = 0; i < 5; i++) {
//                System.out.println();
//            }
//            System.err.println("输入一个新的数字加入树中:  ");
//            int i = scanner.nextInt();
//            System.out.println("*新结构--------------------------");
//            AVLtree.add(i);
//
//            AVLtree.list();
//        }
//    }

}


class AVLTreeNode{

    public AVLTreeNode parent;

    public AVLTreeNode left;

    public AVLTreeNode right;

    public int data;

    public int bf;
}

