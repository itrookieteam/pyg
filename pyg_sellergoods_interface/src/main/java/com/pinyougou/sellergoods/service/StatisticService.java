package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbItem;
import entity.Result;

import java.util.List;

public interface StatisticService {
    public List<TbItem> findAll(String sellerId);
    public TbItem findOne(Long id);
    public Result updateItem(TbItem tbItem);
}
