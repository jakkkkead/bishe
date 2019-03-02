package com.wage.controller;

import com.wage.bean.PageBean;
import com.wage.bean.RestResult;
import com.wage.bean.RestResultGenerator;
import com.wage.bean.TItem;
import com.wage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@CrossOrigin
@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;

    @PostMapping("createItem")
    public RestResult createItem(TItem item) {
        int i = itemService.createItem(item);
        if (i > 0) {
            return RestResultGenerator.createOkResult();
        } else {
            return RestResultGenerator.createFailResult();
        }
    }

    @RequestMapping("getItemList")
    public RestResult selectItem(String beginDate, String endDate, int currentPage, int pageSize, Integer departId) {
        List itemList = itemService.seleceItem(beginDate, endDate, currentPage, pageSize, departId);
        int total = itemService.getCount(beginDate, endDate, departId);
        PageBean pageBean = new PageBean();
        pageBean.setTotal(total);
        pageBean.setList(itemList);
        return RestResultGenerator.createOkResult(pageBean);

    }

    @RequestMapping("updateItem")
    public RestResult updateItem(TItem item) {
        int i = itemService.updateItem(item);
        if (i > 0) {
            return RestResultGenerator.createOkResult();
        } else {
            return RestResultGenerator.createFailResult();
        }
    }

    @RequestMapping("getItemForm")
    public RestResult getItemForm(String year, Integer departId) {
        if (departId != null && departId == 5) {
            departId = null;
        }
        Object obj = itemService.getItemForm(year,departId);
        return RestResultGenerator.createOkResult(obj);
    }
    @RequestMapping("getMapForm")
    public RestResult getMapForm(String year) {
       Object obj = itemService.getItemMapForm(year);

        return RestResultGenerator.createOkResult(obj);
    }
}
