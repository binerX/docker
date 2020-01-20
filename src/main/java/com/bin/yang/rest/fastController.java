package com.bin.yang.rest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bin.yang.biz.DruidBiz;
import com.bin.yang.conf.redis.RedisTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: bin.yang
 * @Date: 2019/1/17 17:37
 * @Description:
 */
@Controller
@RequestMapping(value = "/fast")
public class fastController {

    @Autowired
    private DruidBiz druidBiz;

    @Autowired
    private ElasticController elasticController;

    @Autowired
    private ElasticSeniorController elasticSeniorController;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value = "/hello" , method = RequestMethod.GET)
    @ResponseBody
    public String hello (String id){

        elasticController.DelDocument(id);

        return "hello";
    }

    @RequestMapping(value = "/helloPage" , method = RequestMethod.GET)
    @ResponseBody
    public List<Object> helloPage (){

        List<Object> objects = elasticSeniorController.searchDocument();

        return objects;
    }

    @RequestMapping(value = "/testDruid" , method = RequestMethod.GET)
    @ResponseBody
    public String testDruid (){
        String str = druidBiz.selectInfo(1);
        return str;
    }

    @RequestMapping(value = "/testRedis" , method = RequestMethod.GET)
    @ResponseBody
    public String testRedis (){
        redisTemplate.opsForValue().set("hello" , "fsfdsfs", 5,TimeUnit.MINUTES);
        System.out.println();
        Object hello = redisTemplate.opsForValue().get("hello");

        return hello.toString();
    }

}
