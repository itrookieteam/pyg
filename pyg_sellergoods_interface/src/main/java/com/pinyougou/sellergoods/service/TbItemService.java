package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbItem;

import java.util.List;

public interface TbItemService {

    //根据商品id查询对应sku的信息
    public List findItemList(Long goodsId);



    //根据sku的goodsId查询对应sku具体的信息
    public TbItem findByParentId(Long goodsId);
}
