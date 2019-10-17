package com.bin.yang.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: com.shouzan.sdk.util.Http
 * @Author: bin.yang
 * @Date: 2019/8/20 11:12
 * @Description: TODO
 */
@Slf4j
public class Http {

    public static JSONObject HttpPostTaoQuan(Map<String,String> parms , String url) {
        JSONObject jsonObject = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            httpClient = HttpClients.createDefault();
            //创建httpPost对象
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/x-www-form-urlencoded;charset=UTF-8");

            // 入参
            List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
            parms.forEach((key, value) -> list.add(new BasicNameValuePair(key, value)));
            httpPost.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));

            //发送HttpPost请求，获取返回值
            //调接口获取返回值时，必须用此方法
            CloseableHttpResponse execute = httpClient.execute(httpPost);
            String entity = EntityUtils.toString(execute.getEntity(), "UTF-8");
            jsonObject = JSONObject.parseObject(entity);
        } catch (Exception e) {
            log.error(e.getMessage());
            jsonObject.put("errcode", 1);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                log.error(e.getMessage());
            }
        }
        return jsonObject;
    }

    /**
     * @Description: httpGet请求
     * @param apiUrl
     * @[param] [apiUrl]
     * @return com.alibaba.fastjson.JSONObject
     * @author:  bin.yang
     * @date:  2019/8/20 11:43 AM
     */
    public static JSONObject HttpGetClient(String apiUrl) {
        try {
            HttpClient client = new DefaultHttpClient();
            //发送get请求
            HttpGet request = new HttpGet(apiUrl);
            HttpResponse response = client.execute(request);

            /**请求发送成功，并得到响应**/
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                /**读取服务器返回过来的json字符串数据**/
                String strResult = EntityUtils.toString(response.getEntity());

                return JSON.parseObject(strResult);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
