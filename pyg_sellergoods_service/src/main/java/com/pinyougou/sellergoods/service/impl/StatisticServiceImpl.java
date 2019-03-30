package com.pinyougou.sellergoods.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbItemExample;
import com.pinyougou.sellergoods.service.StatisticService;



import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public List<TbItem> findAll(String sellerId) {
        TbItemExample example = new TbItemExample();
        example.createCriteria().andSellerIdEqualTo(sellerId);
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        return tbItems;
    }
}
