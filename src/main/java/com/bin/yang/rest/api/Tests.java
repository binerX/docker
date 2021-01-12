package com.bin.yang.rest.api;

/**
 * @ClassName: com.bin.yang.rest.api.Tests
 * @Author: bin.yang
 * @Date: 2020/12/18 16:07
 * @Description: TODO
 */
public class Tests {


    public static void main(String[] args) {

        RedBlackTree<Integer> rb = new RedBlackTree<>();
        rb.insert(4);
        rb.insert(5);
        rb.insert(6);
        rb.insert(7);
        rb.insert(8);
        rb.insert(9);
        rb.insert(10);
        rb.insert(11);

        rb.remove(7);
        rb.print();

        System.out.println();
    }
}


