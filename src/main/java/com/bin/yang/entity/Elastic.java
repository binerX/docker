package com.bin.yang.entity;

import com.bin.yang.constant.ObjectRestResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @Auther: bin.yang
 * @Date: 2019/3/1 15:37
 * @Description:
 */
@Data
public class Elastic implements Serializable {

    private static final long serialVersionUID = -2340949926536837921L;

    private String id;

    private String index;

    private String docType;

    private String eventType;

    private HashMap<String,Object> map;

    public Elastic index(String index) {
        this.setIndex(index);
        return this;
    }

    public Elastic docType(String docType) {
        this.setDocType(docType);
        return this;
    }

    public Elastic eventType(String eventType) {
        this.setEventType(eventType);
        return this;
    }

    public Elastic map(HashMap<String,Object> map) {
        this.setMap(map);
        return this;
    }

    public Elastic id(String id) {
        this.setId(id);
        return this;
    }
}
