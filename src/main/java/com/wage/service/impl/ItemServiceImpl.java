package com.wage.service.impl;

import com.wage.bean.TItem;
import com.wage.dao.TItemMapper;
import com.wage.service.ItemService;
import com.wage.util.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private TItemMapper itemMapper;
    @Override
    public int createItem(TItem item) {
        int i = 0;
        String id = UuidUtil.getUUID();
        item.setId(id);
        i = itemMapper.createItem(item);
        return i;
    }

    @Override
    public List seleceItem(String beginDate, String endDate, int currentPage, int pageSize, Integer departId) {
        if( departId == 5 ){
            departId = null;
        }
        int index = (currentPage-1)*pageSize;
        List<TItem> itemList = itemMapper.selectItem(beginDate,endDate,index,pageSize,departId);
        return itemList;
    }

    @Override
    public int getCount(String beginDate, String endDate, Integer departId) {
        if( departId == 5 ){
            departId = null;
        }
        int total = 0;
        total = itemMapper.getCount(beginDate,endDate,departId);
        return total;
    }

    @Override
    public int updateItem(TItem item) {
        int i = itemMapper.updateItem(item);
        return i;
    }
}
