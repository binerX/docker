package com.bin.yang.rest.api;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @ClassName: com.bin.yang.rest.api.BalanceTree
 * @Author: bin.yang
 * @Date: 2020/12/25 17:07
 * @Description: TODO  平衡二叉树
 */
public class BalanceTree<T> {

    private Node root;

    private int size;

    public void insert(T t) {
        if (root == null) {
            root = new Node();
            root.bf = 0;
            root.data = t;
            size++;
            return;
        }
        addNode(root, t);
    }

    private boolean addNode(Node nd, T t) {
        boolean taller = false;
        Comparable<T> cp = (Comparable<T>) nd.data;
        int i = cp.compareTo(t);
        if (i == 0) {
            return false;
        } else if (i > 0) {
            if (nd.lChild == null) {
                Node child = new Node();
                child.bf = 0;
                child.data = t;
                child.parent = nd;
                nd.lChild = child;
                size++;
                if (nd.bf == 0) {
                    nd.bf = 1;
                    return true;
                }
                nd.bf = 0;
            } else {
                taller = addNode(nd.lChild, t);
                if (taller) {
                    if (nd.bf == 1) {
                        leftBalance(nd);
                        taller = false;
                    } else if (nd.bf == 0) {
                        nd.bf = 1;
                        taller = true;
                    } else {
                        nd.bf = 0;
                        taller = false;
                    }
                }
            }
        } else {
            if (nd.rChild == null) {
                Node child = new Node();
                child.bf = 0;
                child.data = t;
                child.parent = nd;
                nd.rChild = child;
                size++;
                if (nd.bf == 0) {
                    nd.bf = -1;
                    return true;
                }
                nd.bf = 0;
            } else {
                taller = addNode(nd.rChild, t);
                if (taller) {
                    if (nd.bf == 1) {
                        nd.bf = 0;
                        taller = false;
                    } else if (nd.bf == 0) {
                        nd.bf = -1;
                        taller = true;
                    } else {
                        rightBalance(nd);
                        taller = false;
                    }
                }
            }
        }
        return taller;
    }

    public int getSize() {
        return size;
    }

    private void leftBalance(Node nd) {
        Node leftChild = nd.lChild;
        if (leftChild.bf == 1) {
            nd.bf = 0;
            leftChild.bf = 0;
            rightRotate(nd);
        } else if (leftChild.bf == -1) {
            Node rd = leftChild.rChild;
            switch (rd.bf) {
                case 1:
                    leftChild.bf = 0;
                    nd.bf = -1;
                    break;
                case 0:
                    leftChild.bf = 0;
                    nd.bf = 0;
                    break;
                case -1:
                    leftChild.bf = 1;
                    nd.bf = 0;
                    break;
            }
            rd.bf = 0;
            leftRotate(leftChild);
            rightRotate(nd);
        }
    }

    private void rightBalance(Node nd) {
        Node rightChild = nd.rChild;
        if (rightChild.bf == 1) {
            Node ld = rightChild.lChild;
            switch (ld.bf) {
                case 1:
                    rightChild.bf = -1;
                    nd.bf = 0;
                    break;
                case 0:
                    rightChild.bf = 0;
                    nd.bf = 0;
                    break;
                case -1:
                    rightChild.bf = 0;
                    nd.bf = 1;
                    break;
            }
            ld.bf = 0;
            rightRotate(rightChild);
            leftRotate(nd);
        } else if (rightChild.bf == -1) {
            nd.bf = 0;
            rightChild.bf = 0;
            leftRotate(nd);
        }

    }

    private void leftRotate(Node nd) {
        Node top = nd.rChild;
        nd.rChild = top.lChild;
        if (top.lChild != null)
            top.lChild.parent = nd;
        top.lChild = nd;
        top.parent = nd.parent;
        if (nd.parent != null) {
            if (nd.parent.lChild == nd)
                nd.parent.lChild = top;
            else
                nd.parent.rChild = top;
        } else {
            root = top;
        }
        nd.parent = top;
    }

    private void rightRotate(Node nd) {
        Node top = nd.lChild;
        nd.lChild = top.rChild;
        if (top.rChild != null)
            top.rChild.parent = nd;
        top.rChild = nd;
        top.parent = nd.parent;
        if (nd.parent != null) {
            if (nd.parent.lChild == nd)
                nd.parent.lChild = top;
            else
                nd.parent.rChild = top;
        } else {
            root = top;
        }
        nd.parent = top;
    }

    public void printTree() {
        ConcurrentLinkedQueue<Node> queue = new ConcurrentLinkedQueue<Node>();
        ConcurrentLinkedQueue<Node> tempQueue = new ConcurrentLinkedQueue<Node>();
        queue.add(root);
        int offset = 0;
        int counter = 2;
        for (int i = 0; i < 50; i++)
            System.out.print(" ");
        while (queue.peek() != null) {
            Node node = queue.poll();
            String side = "L";
            if (node.parent != null && node.parent.rChild == node)
                side = "R";
            System.out.print(node.data + "(" + (node.parent == null ? "" : node.parent.data) + " " + side + ")");
            if (node.parent != null && node.parent.rChild != node)
                for (int i = 0; i < counter; i++)
                    System.out.print(" ");
            if (node.lChild != null)
                tempQueue.add(node.lChild);
            if (node.rChild != null)
                tempQueue.add(node.rChild);
            if (queue.isEmpty()) {
                offset += 3;
                //counter--;
                copyQueue(tempQueue, queue);
                System.out.println();
                for (int i = 0; i < 50 - offset; i++)
                    System.out.print(" ");
            }
        }

    }

    private void copyQueue(ConcurrentLinkedQueue<Node> source,
                           ConcurrentLinkedQueue<Node> target) {
        while (source.peek() != null) {
            target.add(source.poll());
        }
    }

    private class Node<T> {

        public T data;

        public Node lChild;

        public Node rChild;

        public Node parent;

        public int bf;
    }


    public static void main(String[] args) {
        BalanceTree<Object> tree = new BalanceTree<>();
        tree.insert(1);
        tree.insert(2);
        tree.insert(3);
        tree.insert(4);
        tree.insert(6);
        tree.insert(7);
        tree.insert(5);
        tree.insert(4);
    }

}
