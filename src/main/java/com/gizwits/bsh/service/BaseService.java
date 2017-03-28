package com.gizwits.bsh.service;

import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by zhl on 2016/11/23.
 */
public interface BaseService<T,PK extends java.io.Serializable> {

    T selectByPrimaryKey(PK entityId);

    int deleteByPrimaryKey(PK entityId);

    int insert(T record);

    int insertSelective(T record);

    int updateByPrimaryKeySelective(T record);

    int updateByPrimaryKey(T record);

    List<T> selectByExample(Example example);
}