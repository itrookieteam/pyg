package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.sellergoods.service.StatisticService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Reference
    private StatisticService statisticService;

    @RequestMapping("/findAll")
    public List<Map> findAll(){
        //获取sellerId
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        List<TbItem> itemList = statisticService.findAll(sellerId);
        List<Map> maps = new ArrayList<>();
        for (TbItem tbItem : itemList) {
            Map<String, String> map = new HashMap<>();
            map.put("name",tbItem.getTitle());
            map.put("value",tbItem.getNum().toString());
            maps.add(map);
        }
        return maps;
    }

}
