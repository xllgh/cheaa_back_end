package com.gizwits.bsh.bean.reqvo.web;

/**
 * 用户列表
 */
public class UserListReqVO extends DataTableVO {
    /**
     * 搜索类型
     * 用户名 "userName"
     */
    private String searchType;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 显示全列表 on:过滤
     */
    private String listAllFilter;

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getListAllFilter() {
        return listAllFilter;
    }

    public void setListAllFilter(String listAllFilter) {
        this.listAllFilter = listAllFilter;
    }
}
