package com.bin.yang.rest.api;

import java.util.Scanner;

/**
 * @ClassName: com.bin.yang.rest.api.HashContro
 * @Author: bin.yang
 * @Date: 2020/12/18 16:05
 * @Description: TODO
 */
public class HashContro {

    /**
     * 哈希表实现公司雇员信息添加,查找,展示,删除等功能:
     * 1.定义Emp节点信息
     * 2.定义EmpLinkedList链表信息(包括底层add,list,findEmpById,deleteEmpById的代码实现)
     * 3.定义HashTab类:完成哈希表的功能实现
     *
     *
     * */
    public static void main(String[] args) {
        //创建哈希表
        HashTab hashTab = new HashTab(7);

        //写一个简单的菜单
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("add:  添加雇员");
            System.out.println("list: 显示雇员");
            System.out.println("find: 查找雇员");
            System.out.println("exit: 退出系统");

            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("输入id");
                    int id = scanner.nextInt();
                    System.out.println("输入名字");
                    String name = scanner.next();
                    //创建 雇员
                    Emp emp = new Emp(id, name);
                    hashTab.add(emp);
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("请输入要查找的id");
                    id = scanner.nextInt();
                    hashTab.findEmpById(id);
                    break;
                case "delete":
                    System.out.println("请输入要删除额id");
                    id = scanner.nextInt();
                    hashTab.deleteEmpById(id);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                default:
                    break;
            }
        }

    }
}

//定义哈希表类
class HashTab {
    private int size;
    private EmpLinkedList[] empLinkedListArray;

    //初始化

    public HashTab(int size) {
        this.size = size;
        //同时初始化哈希表内部的数组,以及数组里面的存放的链表
        empLinkedListArray = new EmpLinkedList[size];
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i] = new EmpLinkedList();
        }
    }

    //散列函数
    public int hash(int id) {
        return id % size;//利用取模法进行散列处理
    }

    //添加add方法
    public void add(Emp emp) {
        //根据id定位添加到哪一个链表中
        int empLinkedListID = hash(emp.id);
        //进行添加
        empLinkedListArray[empLinkedListID].add(emp);
    }

    //添加list方法
    public void list() {
        //for循环遍历数组,分别调用底层list方法
        for (int i = 0; i < size; i++) {
            empLinkedListArray[i].list(i);
        }
    }

    //添加根据id查找雇员信息的方法
    public void findEmpById(int id) {
        //根据id找到查找对应的链表
        int empLinkedListID = hash(id);
        Emp empById = empLinkedListArray[empLinkedListID].findEmpById(id);
        if (empById != null) {
            System.out.printf("在链表 %d中找到该雇员信息: id = %d;name = %s\n",empLinkedListID + 1,id,empById.name);
        }else {
            System.out.printf("未能在链表 %d中查找到该雇员信息...",empLinkedListID + 1);
        }
        System.out.println();
    }

    //添加根据id删除指定雇员信息的方法
    public void deleteEmpById(int id) {
        int empLinkedListID = hash(id);
        empLinkedListArray[empLinkedListID].deleteEmpById(id);

    }

}

//定义节点类
class Emp {
    public int id;
    public String name;
    public Emp next;

    public Emp(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

//定义链表类
class EmpLinkedList {
    private Emp head = null;//初始化头结点为null

    //添加增加方法
    public void add(Emp emp) {
        //首先判断该链表是否为空
        if (head == null) { //链表为空
            head = emp;//直接将emp添加到头结点处
            return;
        }

        //进行while循环,查找需要添加节点的位置
        Emp temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        //退出while循环,temp此时的位置即是准备添加Emp的位置
        temp.next = emp;
    }

    //添加list方法
    public void list(int no){   //no表示展示链表的编号
        if (head == null) {
            System.out.println("链表" + (no + 1) + ":为空");
            return;
        }
        Emp temp = head;
        System.out.print("链表" + (no + 1) + ":");
        while(true) {
            System.out.printf("==> id = %d;name = %s\t",temp.id,temp.name);
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        System.out.println();
    }

    //添加findEmpByIdf方法
    public Emp findEmpById(int id) {
        if (head == null) {
            //System.out.println("该链表为空...");
            return null;
        }
        Emp temp = head;
        while(true) {
            if (temp.id == id) {
                break;
            }
            if (temp.next == null) {
                temp = null;//将temp置为null
                break;
            }
            temp = temp.next;
        }
        //此时temp即为所要找的雇员信息
        return temp;
    }

    //添加deleteEmpById方法
    public void deleteEmpById(int id) {
        if (head == null) {
            System.out.println("链表为空...无法删除");
            return;
        }

        if (head.id == id) {
            //说明头结点为要删除的节点
            System.out.println("该雇员为VIP,无法删除,请升级权限...");
            return;
        }

        Emp temp = head;
        while (true) {
            if (temp.next.id == id) {   //temp的下一个节点即为要删除的节点信息
                break;
            }
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        //退出while循环
        if (temp.next == null) {
            System.out.println("遍历指定链表,未找到该雇员信息...");
        }else {
            //将temp的next域指向下下个节点,以完成temp下一个节点的删除工作
            temp.next = temp.next.next;
        }
    }
}
