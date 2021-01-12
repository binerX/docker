package com.bin.yang.rest.api;

import java.io.*;
import java.util.*;

/**
 * @ClassName: com.bin.yang.rest.api.HashMapTest
 * @Author: bin.yang
 * @Date: 2020/12/11 14:35
 * @Description: TODO
 */
public class HashMapTest {


    public static void main(String[] args) throws IOException {
        File file = new File("/Users/bin.yang/Desktop/导入模板/test.txt");
        File file1 = new File("/Users/bin.yang/Desktop/导入模板/hashtest.txt");
        if(file1.exists()){
          file1.createNewFile();
        }
        FileOutputStream outputStream = new FileOutputStream(file1);

        FileInputStream inputStream = new FileInputStream(file);
        Scanner scanner = new Scanner(inputStream);
        while(scanner.hasNextLine()){
            String s = scanner.nextLine();
            String i = s.hashCode()+ "";
            outputStream.write(i.getBytes());
            String newLine = System.getProperty("line.separator");
            outputStream.write(newLine.getBytes());
        }

        FileInputStream stream = new FileInputStream(file1);
        Scanner scanner1 = new Scanner(stream);

        // 统计次数
        HashMap<String, Integer> hashMap = new HashMap<>();
        while (scanner1.hasNextLine()){
            String s = scanner1.nextLine();

            if(hashMap.get(s) != null){
                hashMap.put(s, hashMap.get(s) + 1) ;
            }else {
                hashMap.put(s, 1) ;
            }
        }

        Set<Map.Entry<String, Integer>> entries = hashMap.entrySet();
        Iterator<Map.Entry<String, Integer>> iterator1 = entries.iterator();

        while (iterator1.hasNext()){
            System.out.println(iterator1.next().toString());
        }

        Set<String> strings = hashMap.keySet();
        Iterator<String> iterator = strings.iterator();
        while (iterator.hasNext()){
            System.out.println(hashMap.get(iterator.next()));
        }
         String[] str = new String[0];

        // 开始排序
        String[] strings1 = strings.toArray(str);

        System.out.println(strings1);

        quickSort(strings1,0, strings1.length - 1, hashMap);

//        heapSort(strings1,hashMap);

        System.out.println(hashMap);

        for (int i = 0; i < strings1.length; i++) {
            String s = strings1[i];
            System.out.println( hashMap.get(s));
        }

    }

    private static void quickSort(String[] arr, int leftIndex, int rightIndex, HashMap<String, Integer> hashMap) {
        int l;
        int r;
        String t;
        int temp;

        if(leftIndex > rightIndex){
            return;
        }

        l = leftIndex;
        r = rightIndex;
        temp = hashMap.get(arr[leftIndex]);
        String tempKey = arr[leftIndex];

        while (l < r){

            while (l < r && temp <= hashMap.get(arr[r])){
                r--;
            }

            while (l<r && temp >= hashMap.get(arr[l])){
                l++;
            }

            if(l < r){
                t = arr[l];
                arr[l] = arr[r];
                arr[r] = t;
            }
        }

        arr[leftIndex] = arr[l];
        arr[l] =  tempKey;

        quickSort(arr,leftIndex,l -1, hashMap);

        quickSort(arr, r +1, rightIndex , hashMap);



    }


    // 堆排序
    private static void heapSort(String[] arr, HashMap<String, Integer> hashMap) {
        if(arr == null && arr.length == 0){
            return;
        }
        int len = arr.length;
        // 构建最大堆
        buildMacHeap(arr,len,hashMap);
        for (int i = len -1; i >0; i--) {
            swapArr(arr,0, i);
            len--;
            heapify(arr,0,len,hashMap);
        }
    }
    private static void buildMacHeap(String[] arr, int len, HashMap<String, Integer> hashMap) {
        for (int i = (int)Math.floor(len / 1)  -1; i >= 0; i--) {
            heapify(arr, i, len, hashMap);
        }
    }
    private static void heapify(String[] arr, int i, int len, HashMap<String, Integer> hashMap) {
        int left = 2 * i + 1;
        int right = 2*i+2;

        int index = i;
        if(left < len && hashMap.get(arr[left]) > hashMap.get(arr[index])){
            index = left;
        }
        if(right < len && hashMap.get(arr[right]) > hashMap.get(arr[index])){
            index = right;
        }
        if(index != i){
            swapArr(arr,i,index);
            heapify(arr,index,len,hashMap);
        }

    }
    private static void swapArr(String[] arr, int i, int index) {
        String temp = arr[i];
        arr[i] = arr[index];
        arr[index] = temp;
    }


}
