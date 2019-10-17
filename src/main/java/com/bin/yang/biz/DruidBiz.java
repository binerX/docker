package com.bin.yang.biz;

import com.bin.yang.entity.GeoHash;

import javax.persistence.Id;
import java.util.List;

/**
 * @Auther: bin.yang
 * @Date: 2019/1/18 11:37
 * @Description:
 */
public interface DruidBiz {

    String selectInfo(int id);

    List<GeoHash> queryInfoAll();
}
