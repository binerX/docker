package com.bin.yang.rest.api;

/**
 * @ClassName: com.bin.yang.rest.api.Queue
 * @Author: bin.yang
 * @Date: 2020/12/22 15:08
 * @Description: TODO 使用数组模拟队列-编写一个ArrayQueue类
 */
public class Queue {

    // 表示数组的最大容量
    private int size = 16;

    // 该数据用于存放数据, 模拟队列
    private int[] queue;

    // 队列头
    private int front;

    // 队列尾
    private int rear;

    // 创建队列的构造器
    public Queue(){
        this.queue = new int[this.size];
        // 指向队列头部，分析出front是指向队列头的前一个位置.
        this.front = 0;
        // 指向队列尾，指向队列尾的数据(即就是队列最后一个数据)
        this.rear = 0;
    }

    // 判断队列是否满
    public boolean isFull() {
        return rear == size ;
    }

    // 判断队列是否为空
    public boolean isEmpty() {
        return rear == front;
    }

    // 添加数据到队列
    public void add(int val){
        // 判断队列是否满
        if(isFull()){
            System.out.println("队满");
            return;
        }
        // 让rear 后移

        queue[rear] = val;
        rear++;
    }

    // 获取队列的数据, 出队列
    public int pop(){
        // 判断队列是否空
        if (isEmpty()) {
            // 通过抛出异常
            throw new RuntimeException("队列空，不能取数据");
        }

         int val = queue[front];
        // front后移
        front++;
        return val;
    }

    // 显示队列的所有数据
    public void list() {
        // 遍历
        if (isEmpty()) {
            System.out.println("队列空的，没有数据~~");
            return;
        }
        int count = 0;
        for (int i = rear-1; i >= front; i--) {
                System.out.printf("arr[%d]=%d\n", count, queue[i]);
                count++;
        }
    }


    // 上面的队列不能重复使用
    // 优化方式：将数组模拟成环形队列
    //思路：
    //
    //1.front变量的含义做调整：front指向队列的第一个元素，即arr[front]就是队列的第一个元素
    //
    //2.rear变量的含义做调整：rear指向队列的最后一个元素的后一个元素，因为希望空出一个空间作为约定（队列实际容量=maxSize-1，理解为防止指向超出数组范围的地方报错）。
    //
    //3.当队列满时，条件是：(rear + 1) % maxSize == front [满]
    //
    //4.当队列空时，条件是：rear == front [空]
    //
    //5.队列中有效数据的个数：(rear + maxSize - front) % maxSize
    //
    //6.插入队列时，判断队满，先插入队列arr[rear]，然后rear++
    //
    //7.移出队列时，判断队空，先移出队列arr[front]，然后front++

    public void addCircle(int val){

        // 先判断队列是否满
        if(isFull()){
            System.out.println("队列已满");
            return;
        }
        //直接将数据加入
        queue[rear] = val;
        //将 rear 后移, 这里必须考虑取模
        rear = (rear + 1 ) % size;
    }


    public int popCircle(){

        //先判断 是否为空
        if(isEmpty()){
            System.out.println("队列已空");
            throw new RuntimeException("队列是空的");
        }
        // 这里需要分析出 front是指向队列的第一个元素
        // 1. 先把 front 对应的值保留到一个临时变量
        // 2. 将 front 后移, 考虑取模
        // 3. 将临时保存的变量返回

        int val = queue[front];
        front = (front + 1) % size;
        return val;
    }
}
