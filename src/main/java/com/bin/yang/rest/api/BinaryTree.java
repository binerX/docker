package com.bin.yang.rest.api;

import java.util.Scanner;

/**
 * @ClassName: com.bin.yang.rest.api.BinaryTree
 * @Author: bin.yang
 * @Date: 2020/12/24 15:43
 * @Description: TODO
 */
public class BinaryTree {

    public TreeNode treeNode;

    private int height = 0;

    private int lenght = 0;

    public BinaryTree() {


    }

    // 添加节点
    public void add(int val){
        if(treeNode == null){
            treeNode = new TreeNode(val);
            lenght++;
            height++;
            return;
        }

        TreeNode temp = treeNode;
        int count = 1;
        while (true){
            count++;
            if(val < temp.data){
                if(temp.left == null){
                    temp.left = new TreeNode(val);
                    break;
                }
                temp = temp.left;
            }else if(val > temp.data){
                if(temp.right == null){
                    temp.right = new TreeNode(val);
                    break;
                }
                temp = temp.right;
            }else {
                break;
            }
        }
        if(height < count){
            height = count;
        }
        lenght++;
    }

    // 打印节点
    public void list(){
        System.out.println();
        System.out.println();
        listp(treeNode,1);
        System.out.println();
        System.out.println();
    }

    private void listp(TreeNode treeNode,int count) {
        if(treeNode != null){
            listp(treeNode.right, count +1);
            for (int i = 0; i < count; i++) {
                System.out.print("       ");
            }
            System.out.println(treeNode.data);
            listp(treeNode.left,count +1);

        }
    }

    // 打印节点
    public void sortlist(){
        System.out.println();
        System.out.println();
        sortlistp(treeNode);
        System.out.println();
        System.out.println();
    }

    private void sortlistp(TreeNode treeNode) {
        if(treeNode != null){
            sortlistp(treeNode.left);
            System.out.print(treeNode.data + "    ");
            sortlistp(treeNode.right);

        }
    }


    // 删除节点
    public void delete(int val){
        if(treeNode == null){
            return;
        }

        TreeNode temp = treeNode;
        TreeNode min = null;

        // 删除根节点
        if(temp.data == val){
            if(temp.right == null && temp.left == null){
                treeNode = null;
                return;
            }
            if(temp.left != null && temp.right != null ){
                if(temp.right.left == null){
                    temp.data = temp.right.data;
                    temp.right = temp.right.right;
                    return;
                }
                min = temp.right;
                while (true){
                    if(min.left.left == null){
                        break;
                    }
                    min = min.left;
                }

                temp.data = min.left.data;
                min.left = min.left.right;
                return;
            }
            if(temp.right == null){
                treeNode = temp.left;
            }else {
                treeNode = temp.right;
            }
        }

        TreeNode delp = null;
        // 删除子节点
        while (true){
            if(temp == null){
                break;
            }
            if(val > temp.data){
                delp = temp;
                temp = temp.right;
            }else if(val < temp.data){
                delp = temp;
                temp = temp.left;
            }else {
                // 删除叶子节点
                if(temp.right == null && temp.left == null){
                    if(temp.data <  delp.data){
                        delp.left = temp.left;
                    }else {
                        delp.right = temp.right;
                    }
                    return;
                }

                // 左单叉
                if(temp.right == null){
                    if(temp.data <  delp.data){
                        delp.left = temp.left;
                    }else {
                        delp.right = temp.left;
                    }
                    return;
                }

                // 右单叉
                if(temp.left == null){
                    if(temp.data <  delp.data){
                        delp.left = temp.right;
                    }else {
                        delp.right = temp.right;
                    }
                    return;
                }

                // 满叉
                min = temp.right;
                delp = temp;
                if(min.left == null){
                    temp.data = min.data;
                    delp.right = min.right;
                    return;
                }
                while (true){
                    if(min.left == null){
                       break;
                    }
                    delp = min;
                    min = min.left;
                }
                temp.data = min.data;
                delp.left = min.right;
                break;
            }
        }
    }

/*
    public static void main(String[] args) {
       BinaryTree tree = new BinaryTree();
        tree.add(88);
        tree.add(66);
        tree.add(52);
        tree.add(2);
        tree.add(42);
        tree.sortlist();
        tree.list();
//        tree.delete(232);
//        tree.list();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            for (int i = 0; i < 5; i++) {
                System.out.println();
            }
            System.err.println("输入一个新的数字加入树中:  ");
            int i = scanner.nextInt();
            System.out.println("*新结构--------------------------");
            tree.add(i);

            tree.list();
        }
    }*/


}

class TreeNode{

    public int data;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int i) {
        this.data = i;
        this.left = null;
        this.right = null;
    }

}