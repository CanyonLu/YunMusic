package com.lcy.util.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 表格数据对象
 * @author lucy
 * @date Created in 2022/12/13
 */
public class TableDataInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private long count;

    private List<?> data;

    private int code;

    private String msg;

    public TableDataInfo(){

    }

    public TableDataInfo(List<?> list, int count){
        this.data = list;
        this.count = count;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
