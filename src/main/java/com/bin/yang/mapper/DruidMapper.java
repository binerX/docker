package com.bin.yang.mapper;

import com.bin.yang.entity.GeoHash;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Auther: bin.yang
 * @Date: 2019/1/18 11:42
 * @Description:
 */
@Mapper
public interface DruidMapper {

    String selectInfo(int id);

    List<GeoHash> queryInfoAll();
}
