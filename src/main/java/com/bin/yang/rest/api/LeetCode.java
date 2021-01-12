package com.bin.yang.rest.api;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;

/**
 * @ClassName: com.bin.yang.rest.api.LeetCode
 * @Author: bin.yang
 * @Date: 2020/12/15 11:26
 * @Description: TODO
 */
public class LeetCode {

    public static void main(String[] args) {

        /*
        * 给定方程[α1χ1³+α2χ2³+α3χ3³+α4χ4³+α5χ5³=0]，请写一个程序，从控制台输入[α1,α2,α3,α4,α5]，然后求共有多少整数解，其中
          限定[-50<= χ1,χ2,χ3,χ4,χ5 < 50]。
          这道题目肯定不能用暴力枚举，100^5的计算量肯定是不行的。第一步，我们可以把方程 左边的第四项和第五项进行移项，
          则方程就变成[α1χ1³+α2χ2³+α3χ3³ = -(α4χ4³+α5χ5³)]。如果等式的左右两部分相等，那么就找到了原方程的一个解。通过这样的转换，这道题目就从枚举变成了查找。
          也就是从前一项的所有结果中查找后一项的结果。前一项的结果共有一百万个数，而后一项只有一万个数，这样我们可以考虑使用散列表对数据进行组织。
          如果后一项的某一次计算结果是A，那么要在散列表中找出一共有多少个A。
        * */
        Random r = new Random();
        solve(r.nextInt(160),r.nextInt(162),r.nextInt(163),r.nextInt(164),r.nextInt(116));
    }

    public static void solve(int a1, int a2, int a3, int a4, int a5) {
        System.out.println(a1+","+a2+","+a3+","+a4+","+a5);
        HashMap<Integer, Integer> m = new HashMap<>();
        for (int i = -50; i < 50; i++) {
            for (int j = - 50; j < 50; j++) {
                for (int k = -50; k < 50; k++) {
                    int t = a1 * i * i * i + a2 * j * j * j + a3 * k * k * k;
                    Integer p = m.get(t);
                    if (p == null)
                        m.put(t, 1);
                    else
                        m.put(t, p + 1);
                }
            }
        }

        int sum = 0;
        for (int i = -50; i < 50; i++) {
            for (int j = -50; j < 50; j++) {
                int t = a4 * i * i * i +  a5 * j * j * j;
                Integer p = m.get(-t);

                if (p != null)
                    sum += p;
            }
        }

        System.out.print(sum);
    }



    /**
     * 八皇后问题（回溯算法）
     *
     * 八皇后问题，是一个古老而著名的问题，是回溯算法的典型案例。该问题是国际西洋棋棋手马克斯·贝瑟尔于1848年提出：
     * 在8×8格的国际象棋上摆放八个皇后，使其不能互相攻击，即：任意两个皇后都不能处于同一行、同一列或同一斜线上，
     * 问有多少种摆法。
     *
     * */

    private static int COUNT;
    private static int TOTAL;

//    public static void main(String[] args) {
//
//        System.out.println("初始棋盘");
//        int[][] arr = new int[8][8];
//        for (int i = 0; i < arr.length; i++) {
//            for (int j = 0; j < arr.length; j++) {
//                System.out.print(arr[i][j]+" ");
//            }
//            System.out.println();
//        }
//        System.out.println();
//
//        for (int i = 0; i < arr.length; i++) {
//            arr = new int[8][8];
//            arr[0][i] = 1;
//            COUNT = 0;
//            System.out.println("第"+(i+1)+"次放置棋子");
//            for (int q = 0; q < arr.length; q++) {
//                for (int j = 0; j < arr.length; j++) {
//                    System.out.print(arr[q][j]+" ");
//                }
//                System.out.println();
//            }
//            System.out.println();
//            eightqueen(arr,1,0,1);
//            System.out.println();
//            System.out.println("=====总计完成" +(TOTAL+=COUNT));
//            System.out.println();
//        }
//    }

    public static void eightqueen(int[][] arr, int r, int l,int t) {

        if(r == 0){
            return;
        }

        if (r == 8){
            COUNT++;
            System.out.println();
            System.out.println("*****完成"+COUNT+"次******");
            for (int i = 0; i < arr.length; i++) {
                for (int j = 0; j < arr.length; j++) {
                    if(arr[i][j] == -1){
                        System.out.print(0+" ");
                    }else {
                        System.out.print(arr[i][j]+" ");
                    }
                }
                System.out.println();
            }

            for (int i = 1; i < arr.length; i++) {
                for (int j = 0; j < arr.length; j++) {
                    if((arr[i][j] == 1 || arr[i][j] == -1) && i == 1){
                        arr[i][j] = -1;
                    }else {
                        arr[i][j] = 0;
                    }
                }
            }
            eightqueen(arr, (r+1) % 8,(l+1) % 8,1);
            return;
        }

        if(t == 8){
            if(r == 1){
                return;
            }
            eightqueen(arr, r -1,(l +1) % 8,1);
            return;
        }

        boolean succ = true;

        int top = r;

        int left  = l;

        int right = l;

        t++;

        // 假设放置当前位置 r , l
        for (int i = 0; i < r ; i++) {
            top--;
            right++;
            left--;

            if(arr[top][l] == 1){
                succ = false;
                break;
            }

            if(right <8 && arr[top][right] == 1){
                succ = false;
                break;
            }

            if(left >= 0 && arr[top][left] == 1){
                succ = false;
                break;
            }
        }
        if(succ){
            if(arr[r][l] == 0){
                for (int i = r; i < arr.length; i++) {
                    for (int j = 0; j < arr.length; j++) {
                        if((arr[i][j] == 1 || arr[i][j] == -1) && i == r){
                            arr[i][j] = -1;
                        }else {
                            arr[i][j] = 0;
                        }
                    }
                }
                arr[r][l] = 1;
                eightqueen(arr, r+1,l, 1);
                return;
            }
        }
        eightqueen(arr, r,(l+1 )% 8, t);
    }



}
