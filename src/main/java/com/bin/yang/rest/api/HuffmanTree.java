package com.bin.yang.rest.api;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: com.bin.yang.rest.api.HuffmanTree
 * @Author: bin.yang
 * @Date: 2020/12/31 14:54
 * @Description: TODO   哈弗曼树
 */
public class HuffmanTree {

    private HNode HTree;


    public void add(List<HNode> list) {

        int start = 0;
        while (start < list.size() - 1) {
            quickSort(list);

            HNode left = list.get(start);
            start++;
            HNode right = list.get(start);

            HNode parent = new HNode(null, left.weight + right.weight);
            parent.left = left;
            parent.right = right;
            list.set(start, parent);
            HTree = parent;
        }
    }

    public void list() {
        listP(HTree);
    }

    static String b = "";

    private void listP(HNode t) {
        if (t != null) {
            b+="0";
            listP(t.left);
            if(StringUtils.isNotBlank(t.data)){
                System.out.print(b+"       >>>     ");
                System.out.println(t.toString());
            }
            b+="1";
            listP(t.right);
        }
        if(StringUtils.isNotBlank(b)){
            b = b.substring(0,b.length() -1);
        }
    }

    private void quickSort(List<HNode> list) {
        sort(list, 0, list.size() - 1);
    }

    private void sort(List<HNode> list, int l, int r) {
        int left;
        int right;
        HNode t;
        HNode temp;
        if (l > r) {
            return;
        }
        left = l;
        right = r;
        temp = list.get(l);

        while (left < right) {
            //  在看右边
            while (right > left && list.get(right).weight >= temp.weight) {
                right--;
            }

            // 先看左边
            while (left < right && list.get(left).weight <= temp.weight) {
                left++;
            }
            if (left < right) {
                t = list.get(left);
                list.set(left, list.get(right));
                list.set(right, t);
            }
        }

        list.set(l, list.get(left));
        list.set(left, temp);

        // 左递归
        sort(list, l, left - 1);
        // 右递归
        sort(list, right + 1, r);
    }

   /* public static void main(String[] args) {
        HuffmanTree Htree = new HuffmanTree();
        List<HNode> hNodes = new ArrayList<>();

        HNode a = new HNode("a", 50);
        hNodes.add(a);
        HNode d = new HNode("d", 3);
        hNodes.add(d);
        HNode f = new HNode("f", 40);
        hNodes.add(f);
        HNode g = new HNode("g", 8);
        hNodes.add(g);
        HNode h = new HNode("h", 23);
        hNodes.add(h);
        HNode b = new HNode("b", 100);
        hNodes.add(b);

        Htree.add(hNodes);

        System.out.println();

        Htree.list();
    }*/

}

class HNode {

    public String data;

    public int weight;

    public HNode left;

    public HNode right;

    public HNode(String data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return data + "  数据 | 权重" + weight;
    }
}