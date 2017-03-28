package com.gizwits.bsh.bean.resvo.web;

import java.util.ArrayList;
import java.util.List;

/**
 * 配合前端插件“DataTable”的使用，将每次查询出来的数据，返回到前端插件。
 * Created by Matthew on 2015/8/12.
 */
public class ReviewVO<T> {

    /** 交互次数：由前端插件提供，我们只需转换为整数，原样返回即可，这里出于安全考虑的‘盐’ */
    private Integer draw;
    /** 总条数（没有过滤操作的记录数） */
    private Integer recordsTotal=0;
    /** 结果条数（过滤后的记录数，若无过滤，直接返回总条数） */
    private Integer recordsFiltered=0;
    /** 返回的结果，对象数组 */
    private List<T> data=new ArrayList<>();
    /** 错误信息（反馈的信息） */
    private String error="";

    public ReviewVO(){}

    public ReviewVO(Integer draw, Integer recordsTotal, Integer recordsFiltered, List<T> data, String error) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
        this.error = error;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "RevieVO{" +
                "draw=" + draw +
                ", recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", data=" + data +
                ", error='" + error + '\'' +
                '}';
    }
}
