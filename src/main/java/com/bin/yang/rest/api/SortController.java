package com.bin.yang.rest.api;

import java.util.Arrays;

/**
 * @ClassName: com.bin.yang.rest.api.SortController
 * @Author: bin.yang
 * @Date: 2020/12/11 17:31
 * @Description: TODO
 */
public class SortController {

    public static void main(String[] args) {

        int[] arr  = {3,42,67,9,23,4,12,1,76,54,234,73,231,22,72,235,38};

//        System.out.println(Arrays.toString(arr));
//        quickSort(arr,0,arr.length-1);
//        System.out.println(Arrays.toString(arr));

//        System.out.println(Arrays.toString(arr));
//        heapSort(arr);
//        System.out.println(Arrays.toString(arr));

//        System.out.println(Arrays.toString(arr));
//        mergeSort(arr);
//        System.out.println(Arrays.toString(arr));

//        System.out.println(Arrays.toString(arr));
//        selectionSort(arr);
//        System.out.println(Arrays.toString(arr));

        System.out.println(Arrays.toString(arr));
        insertionSort(arr);
        System.out.println(Arrays.toString(arr));

    }

    private static void insertionSort(int[] arr) {

        int length = arr.length;
        int current;
        int preIndex;

        for (int i = 1; i < length; i++) {
            current = arr[i];
            preIndex = i -1;

            while (preIndex >= 0 && arr[preIndex] > current){
                arr[preIndex +1] = arr[preIndex];
                preIndex--;
            }
            arr[preIndex +1] = current;
        }
    }


    // 选择排序
    private static void selectionSort(int[] arr) {

        int minIndex;
        for (int i = 0; i < arr.length -1; i++) {
            minIndex = i;
            for (int j = i +1; j < arr.length; j++) {
                if(arr[j] < arr[minIndex]){
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }


    // 归并排序
    public static void mergeSort(int[] arr) {
        // 先建好一个临时数组, 避免递归中重复创建
        int[] temp = new int[arr.length];
        sort(arr, 0, arr.length -1, temp);
    }

    private static void sort(int[] arr, int l, int r, int[] temp) {
        if(l >= r){
            return;
        }
        int mid = (l + r) / 2;
        // 先递归左半组 , 使其子数组有序
        sort(arr, l, mid, temp);
        // 在递归右半组, 使其子数组有序
        sort(arr, mid +1, r, temp);
        // 将左右两个有序子数组合并并排序
        merge(arr, l, mid, r, temp);
    }

    private static void merge(int[] arr, int l, int mid, int r, int[] temp) {

        // 左序列指针
        int left = l;
        // 右序列指针
        int right = mid +1;
        // 临时指针
        int t = 0;

        while (left <= mid && right <= r){
            if(arr[left] <= arr[right]){
                temp[t++] = arr[left++];
            }else {
                temp[t++] = arr[right++];
            }
        }

        while (left <= mid){
            temp[t++] = arr[left++];
        }

        while (right <= r){
            temp[t++] = arr[right++];
        }

        t = 0;
        while (l <= r){
            arr[l++] = temp[t++];
        }
    }

    // 堆排序
    public static void  heapSort(int[] arr){

        if(arr == null || arr.length == 0){
            return;
        }

        int length = arr.length;

        // 构建大顶堆
        buildMacHeap(arr,length);

        // 交换堆顶和当前末尾的节点，重置大顶堆
        for (int i = length - 1; i > 0; i--) {
            swap(arr, 0, i);
            length--;
            heapify(arr, 0, length);
        }
    }

    private static void buildMacHeap(int[] arr, int length) {
        for (int i = (int)Math.floor(length / 2) -1; i >= 0; i--) {
            heapify(arr, i, length);
        }
    }

    private static void heapify(int[] arr, int i, int length) {
        // 先根据堆性质，找出它左右节点的索引
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        // 默认当前节点（父节点）是最大值。
        int largestIndex = i;

        if(left < length && arr[left] > arr[largestIndex]){
            // 如果有左节点，并且左节点的值更大，更新最大值的索引
            largestIndex = left;
        }
        if(right <  length && arr[right] > arr[largestIndex]){
            // 如果有右节点，并且右节点的值更大，更新最大值的索引
            largestIndex = right;
        }

        if(largestIndex != i){

            // 如果最大值不是当前非叶子节点的值，那么就把当前节点和最大值的子节点值互换
            swap(arr, i, largestIndex);

            // 因为互换之后，子节点的值变了，如果该子节点也有自己的子节点，仍需要再次调整。
            heapify(arr, largestIndex, length);

        }
    }

    public static void swap(int[] arr,  int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // 快速排序
    private static void quickSort(int[] arr, int leftIndex, int rightIndex) {

        int l;
        int r;
        int t;
        int temp;

        if (leftIndex > rightIndex){
            return;
        }

        l = leftIndex;
        r = rightIndex;

        // 基准位
        temp = arr[leftIndex];

        while (l < r){

            // 先看右边
            while (temp <= arr[r] && l < r){
                r--;
            }

            while (temp >= arr[l] && l < r){
                l++;
            }

            // 满足条件 交换
            if(l < r){
                t = arr[l];
                arr[l] = arr[r];
                arr[r] = t;
            }

        }

        // 最后将基准位 和 LR 进行交换
        arr[leftIndex] = arr[l];
        arr[l] = temp;

        // 递归排序左半组
        quickSort(arr, leftIndex, l - 1);

        // 递归排序右半组
        quickSort(arr, r + 1, rightIndex);
    }
}
