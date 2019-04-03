package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.pinyougou.mapper.TbItemMapper;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.sellergoods.service.TbItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class TbItemServiceImpl implements TbItemService{


    @Autowired
    private TbItemMapper itemMapper;

    //根据商品id查询对应sku的信息

    public List findItemList(Long goodsId) {
        List<TbItem> itemList = itemMapper.selectTbItem(goodsId);
        System.out.println(itemList);
        return itemList;

    }


    //根据sku的goodsId查询对应sku具体的信息

    public TbItem findByParentId(Long goodsId) {
        return itemMapper.selectByPrimaryKey(goodsId);
    }
}
