package com.gizwits.bsh.bean.reqvo.web;

import javax.validation.constraints.NotNull;

/**
 * Created by Matthew on 2015/8/13.
 */
public class DataTableVO {

    /** 交互次数：由前端插件提供，我们只需转换为整数，原样返回即可，这里出于安全考虑的‘盐’ */

    @NotNull
    private Integer draw;
    /** 从第几条开始 */
    @NotNull
    private Integer start;
    /** 取start后的多少条数据 */
    @NotNull
    private Integer length;


    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

}
