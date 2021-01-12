package com.bin.yang.rest.api;

import java.util.*;

/**
 * @ClassName: com.bin.yang.rest.api.ApiController
 * @Author: bin.yang
 * @Date: 2020/12/10 16:39
 * @Description: TODO
 */
public class ApiController {

    public static void main(String[] args) {

        int i = "第三方规范的工地上的1分公司个发生过沙发上孙菲菲是发送可根据可好好和集合卡卷好几款好几款好看接口广告看过看刚看过客户个看看看".hashCode();
        int i1 = "第三方规范的工地上的2分公司个发生过沙发上孙菲菲是发送可根据可好好和集合卡卷好几款好几款好看接口广告看过看刚看过客户个看看看".hashCode();
        int i2 = "第三方规范的工地上的11分公司个发生过沙发上孙菲菲是发送可根据可好好和集合卡卷好几款好几款好看接口广告看过看刚看过客户个看看看".hashCode();
        int i3 = "第三方规范的工地上的1分公司个发生过沙发上孙菲菲是发送可根据可好好和集合卡卷好几款好几款好看接口广告看过看刚看过客户个看看看".hashCode();
        int i4 = "第三方规范的工地上的分公司个发生过沙发上孙菲菲是发送可根据可好好和集合卡卷好几款好几款好看接口广告看过看刚看过客户个看看看".hashCode();
        int i5 = "第三方规范的工地上的1分公司个发生过沙发上孙菲菲是发送可根据可好好和集合卡卷好几款好几款好看接口广告看过看刚看过客户个看看看".hashCode();


        System.out.println(Math.abs(i % 1000));
        System.out.println(Math.abs(i1% 1000));
        System.out.println(Math.abs(i2% 1000));
        System.out.println(Math.abs(i3% 1000));
        System.out.println(Math.abs(i4% 1000));
        System.out.println(Math.abs(i5% 1000));

        int abs = Math.abs(i % 1000);
        int abs1 = Math.abs(i1 % 1000);

        int abs2 = Math.abs(i2 % 1000);

        int abs3 = Math.abs(i3 % 1000);
        int abs4 = Math.abs(i4 % 1000);
        int abs5 = Math.abs(i5 % 1000);



        Set<Object> set = new HashSet<>();
        set.add(abs);
        set.add(abs1);
        set.add(abs2);
        set.add(abs3);
        set.add(abs4);
        set.add(abs5);
        System.out.println("-=-=-=-=-==-=-=-=-=-=-=-");

        Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

        Set<Object> objects = new TreeSet<>();
        objects.add(abs);
        objects.add(abs1);
        objects.add(abs2);
        objects.add(abs3);
        objects.add(abs4);
        objects.add(abs5);
        System.out.println("-=-=-=-=-==-=-=-=-=-=-=-");
        System.out.println(objects);

        Iterator<Object> iterator1 = objects.iterator();
        System.out.println("-=-=-=-=-==-=-=-=-=-=-=-");
        while (iterator1.hasNext()){
            System.out.println(iterator1.next());
        }
    }

}
