package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.pinyougou.sellergoods.service.StatisticService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Reference
    private StatisticService statisticService;

    @RequestMapping("/findAll")
    public List findAll(){
        //获取sellerId
        String sellerId = SecurityContextHolder.getContext().getAuthentication().getName();
        FastJsonConfig config = new FastJsonConfig();
        System.out.println(statisticService.findAll(sellerId));
        return statisticService.findAll(sellerId);
    }

}
