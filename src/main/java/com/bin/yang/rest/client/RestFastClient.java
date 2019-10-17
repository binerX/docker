package com.bin.yang.rest.client;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * @Auther: bin.yang
 * @Date: 2019/2/27 14:59
 * @Description:
 */
public class RestFastClient {

    private static final String host="106.13.34.237";  //服务器地址

    private static final int port =9200; //端口

    private static final String quest ="http"; //端口

    private RestHighLevelClient client;

    public  RestHighLevelClient getClient() {

         //rest高级客户端实例需要一个REST低级别的客户端生成器 来构建
         client = new RestHighLevelClient(
                RestClient.builder(new HttpHost(host, port, quest),
                        new HttpHost(host, port, quest)));

        return client;
    }

    public void closeClient() {
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
