package com.bin.yang.rest.api;

import org.roaringbitmap.RoaringBitmap;

/**
 * @ClassName: com.bin.yang.rest.api.RoaringBitMap
 * @Author: bin.yang
 * @Date: 2020/12/18 16:00
 * @Description: TODO
 */
public class RoaringBitMap {

    public static void main(String[] args) {
        RoaringBitmap bitmap = new RoaringBitmap();
        bitmap.add(3);
        bitmap.add(8);
        bitmap.add(1949324323);
        boolean contains = bitmap.contains(2);
        System.out.println(contains);
        String s = bitmap.toString();
        System.out.println(s);
        int[] ints = bitmap.toArray();
        for (int i = 0; i < ints.length; i++) {
            int anInt = ints[i];
            System.out.println(anInt);
        }
    }

}
