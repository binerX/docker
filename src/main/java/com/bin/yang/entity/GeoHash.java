package com.bin.yang.entity;

import lombok.Data;

import java.io.Serializable;
import java.lang.annotation.Documented;

/**
 * @Auther: bin.yang
 * @Date: 2019/2/14 15:28
 * @Description:
 */
@Data
public class GeoHash implements Serializable {

    private static final long serialVersionUID = 3503165446378927996L;

    private Integer id;

    private String name;

    private Integer age;

    private String adrss;

    private Integer sex;

}
