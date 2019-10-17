package com.bin.yang.rest;

import com.bin.yang.biz.DruidBiz;
import com.bin.yang.constant.ObjectRestResponse;
import com.bin.yang.entity.GeoHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Auther: bin.yang
 * @Date: 2019/2/14 15:51
 * @Description:
 */
@Controller
@RequestMapping("geo")
public class GeoHashController {

    @Autowired
    private DruidBiz druidBiz;

    @RequestMapping(value = "geoh" , method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse getGeoh(){
         List<GeoHash> list = druidBiz.queryInfoAll();
        return new ObjectRestResponse().rows(list);
    }

}
