package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbItem;

import java.util.List;

public interface StatisticService {
    public List<TbItem> findAll(String sellerId);
}
