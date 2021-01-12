package com.bin.yang.rest.api;

/**
 * @ClassName: com.bin.yang.rest.api.DoubleLinkedList
 * @Author: bin.yang
 * @Date: 2020/12/22 16:23
 * @Description: TODO
 */
public class DoubleLinkedList {

    private DoubleNode head = new DoubleNode(0,"");


    // 返回头节点
    public DoubleNode getHead() {
        return head;
    }

    // 遍历双向链表的方法
    // 显示链表[遍历]
    public void list() {
        // 判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }
        // 因为头节点，不能动，因此我们需要一个辅助变量来遍历
        DoubleNode temp = head.next;
        while (true) {
            // 判断是否到链表最后
            if (temp == null) {
                break;
            }
            // 输出节点的信息
            System.out.println(temp);
            // 将temp后移， 一定小心
            temp = temp.next;
        }
    }

    // 添加一个节点到双向链表的最后.
    public void add(DoubleNode node) {

        // 因为head节点不能动，因此我们需要一个辅助遍历 temp
        DoubleNode temp = head;
        // 遍历链表，找到最后
        while (true) {
            // 找到链表的最后
            if (temp.next == null) {//
                break;
            }
            // 如果没有找到最后, 将将temp后移
            temp = temp.next;
        }

        // 当退出while循环时，temp就指向了链表的最后
        // 形成一个双向链表
        temp.next = node;
        node.prev = temp;
    }


    // 修改一个节点的内容, 可以看到双向链表的节点内容修改和单向链表一样
    // 只是 节点类型改成 HeroNode2
    public void update(DoubleNode node) {
        // 判断是否空
        if (head.next == null) {
            System.out.println("链表为空~");
            return;
        }
        // 找到需要修改的节点, 根据no编号
        // 定义一个辅助变量
        DoubleNode temp = head.next;
        boolean flag = false; // 表示是否找到该节点
        while (true) {
            if (temp == null) {
                break; // 已经遍历完链表
            }
            if (temp.no == node.no) {
                // 找到
                flag = true;
                break;
            }
            temp = temp.next;
        }
        // 根据flag 判断是否找到要修改的节点
        if (flag) {
            temp.val = node.val;
        } else { // 没有找到
            System.out.printf("没有找到 编号 %d 的节点，不能修改\n", node.no);
        }
    }

    // 从双向链表中删除一个节点,
    // 说明
    // 1 对于双向链表，我们可以直接找到要删除的这个节点
    // 2 找到后，自我删除即可
    public void delete(DoubleNode node){

        // 判断当前链表是否为空
        if(head.next == null){
            System.out.println("链表为空，无法删除");
            return;
        }

        // 辅助变量(指针)
        // 标志是否找到待删除节点的
        DoubleNode temp = head.next;
        boolean flag = false;

        while (true){
            // 找到的待删除节点的temp
            if(temp.no == node.no){
                flag = true;
                break;
            }
            if(temp.next == null){
                // 已经到链表的最后
                break;
            }
            // temp后移，遍历
            temp = temp.next;
        }

        // 判断flag
        if(flag){
            // 可以删除
            // temp.next = temp.next.next;[单向链表]
            temp.prev.next = temp.next;
            // 如果是最后一个节点，就不需要执行下面这句话，否则出现空指针
            if (temp.next != null) {
                temp.next.prev = temp.prev;
            }
        }else {
            System.out.printf("要删除的 %d 节点不存在\n", node);
        }
    }
}

//定义HeroNode ， 每个HeroNode 对象就是一个节点
class DoubleNode{

    public int no;

    public String val;

    //指向下一个节点 , 默认为null
    public DoubleNode next;

    // 指向前一个节点 , 默认为null
    public DoubleNode prev;

    public DoubleNode(int no,String val){
        this.no = no;
        this.val = val;
    }

    //为了显示方法，我们重新toString
    @Override
    public String toString() {
        return "DoubleNode [no=" + no + ", name=" + val + "]";
    }
}