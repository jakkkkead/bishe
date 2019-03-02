package com.wage.dao;

import com.wage.bean.ExpenseForms;
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

    public List getItemFormsToStatus(@Param("year")String year,@Param("departId")Integer departId,@Param("status")Integer status,@Param("group")String group);
    public List getItemFormsToType(@Param("year")String year,@Param("departId")Integer departId,@Param("status")Integer status,@Param("group")String group);
    public List getItemMapForm (@Param("year") String year);
}
