package com.wage.dao;

import com.wage.bean.TItem;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface TItemMapper {

    public int createItem(TItem item);
    public List selectItem(@Param("beginDate") String beginDate,@Param("endDate")String endDate,@Param("index")int index,@Param("pageSize")int pageSize, @Param("departId")Integer departId);
    public int getCount(@Param("beginDate") String beginDate,@Param("endDate")String endDate,@Param("departId")Integer departId);
    public int updateItem(TItem item);
}
