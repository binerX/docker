package com.bin.yang.rest.api;

/**
 * @ClassName: com.bin.yang.rest.api.Stack
 * @Author: bin.yang
 * @Date: 2020/12/22 14:44
 * @Description: TODO  栈
 */
public class Stack {

    // 栈大小
    private int size = 16;

    // 栈数组 数组模拟栈，数据就放在该数组
    private int[] stack;

    // top表示栈顶，初始化为-1
    private int top = -1;

    // 构造器
    public  Stack(){
        this.stack = new int[this.size];
    }

    // 栈满
    public boolean isFull(){
        return top == size - 1;
    }

    // 栈满
    public boolean isEmpty(){
        return top == -1;
    }

    // 入栈
    public void push(int val){
        //先判断栈是否已满
        if(isFull()){
            System.out.println("栈满");
            return;
        }

        top++;
        stack[top] = val;
    }

    // 出栈pop, 将栈顶的数据返回
    public int pop(){
        //先判断栈是否空
        if(isEmpty()){
            System.out.println("栈空");
            throw new RuntimeException("栈空");
        }
        int val = stack[top];
        top--;
        return val;
    }

    // 显示栈的情况[遍历栈]， 遍历时，需要从栈顶开始显示数据
    public void list(){
        if(isEmpty()) {
            System.out.println("栈空，没有数据~~");
            return;
        }
        for (int i = top; i >= 0 ; i--) {
            System.out.printf("stack[%d]=%d\n", i, stack[i]);
        }
    }
}
