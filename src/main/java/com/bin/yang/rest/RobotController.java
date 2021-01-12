package com.bin.yang.rest;

import com.alibaba.fastjson.JSONObject;
import com.bin.yang.constant.Constant;
import com.bin.yang.util.Http;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * @ClassName: com.bin.yang.rest.RobotController
 * @Author: bin.yang
 * @Date: 2019/9/26 10:45
 * @Description: TODO
 */
@Controller
@RequestMapping(value = "/api")
public class RobotController {

    @RequestMapping(value = "/robot", method = RequestMethod.GET)
    public String robotGo(){
        return "/index";
    }

    @RequestMapping(value = "/img", method = RequestMethod.GET)
    public String img(){
        return "/ffgaliao.jpg";
    }

    @RequestMapping(value = "/getMsg", method = RequestMethod.GET)
    @ResponseBody
    public String getMsg(String keyword){
        System.out.println(new Date().toLocaleString()+"-----"+"参数 : "+keyword);
        JSONObject jsonObject = Http.HttpGetClient(Constant.URL+keyword);
        if(jsonObject.getString("result").equals("0")){
            String content = jsonObject.getString("content");
            if(content.indexOf("{br}") > -1){
                content = content.replace("{br}", "\n");
            }
            System.out.println(new Date().toLocaleString()+"-----"+content);
            return content;
        }
        return "稍后再试 , 正在策划大事 . ";
    }
}
