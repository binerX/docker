package com.bin.yang.biz.impl;

import com.bin.yang.biz.DruidBiz;
import com.bin.yang.entity.GeoHash;
import com.bin.yang.mapper.DruidMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: bin.yang
 * @Date: 2019/1/18 11:39
 * @Description:
 */
@Service
public class DruidBizImpl implements DruidBiz {

    @Autowired
    private DruidMapper druidMapper;

    @Override
    public String selectInfo(int id) {
        System.out.println(1);
        return druidMapper.selectInfo(id);
    }

    @Override
    public List<GeoHash> queryInfoAll() {
        return druidMapper.queryInfoAll();
    }
}
