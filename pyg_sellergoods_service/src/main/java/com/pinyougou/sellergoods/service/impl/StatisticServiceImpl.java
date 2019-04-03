package com.pinyougou.sellergoods.service.impl;

import entity.Result;
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
        //添加条件，查询该商家的所有商品规格信息，并且已上架
        example.createCriteria().andSellerIdEqualTo(sellerId).andStatusEqualTo("1");
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        return tbItems;
    }

    @Override
    public TbItem findOne(Long id) {
        return itemMapper.selectByPrimaryKey(id);
    }

    @Override
    public Result updateItem(TbItem tbItem) {

        try{
            itemMapper.updateByPrimaryKey(tbItem);
            return new Result(true,"修改库存成功！");
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,"修改库存失败！");
        }
    }
}
