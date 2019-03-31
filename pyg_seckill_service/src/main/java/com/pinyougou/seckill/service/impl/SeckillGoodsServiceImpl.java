package com.pinyougou.seckill.service.impl;
import java.util.Date;
import java.util.List;

import entity.SeckillGoods;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSeckillGoodsMapper;
import com.pinyougou.pojo.TbSeckillGoods;
import com.pinyougou.pojo.TbSeckillGoodsExample;
import com.pinyougou.pojo.TbSeckillGoodsExample.Criteria;
import com.pinyougou.seckill.service.SeckillGoodsService;

import entity.PageResult;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SeckillGoodsServiceImpl implements SeckillGoodsService {

	@Autowired
	private TbSeckillGoodsMapper seckillGoodsMapper;

	@Autowired
	private RedisTemplate redisTemplate;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeckillGoods> findAll() {
		//从redis中获取符合条件的秒杀商品
		//return seckillGoodsMapper.selectByExample(null);
		return redisTemplate.boundHashOps("seckill_goods").values();
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSeckillGoods> page=   (Page<TbSeckillGoods>) seckillGoodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */

	public void add(SeckillGoods goods) {


		   // 创建 一个秒杀的对象
		TbSeckillGoods seckillGoods = goods.getTbSeckillGoods();

		 //获取出对象封装对象的属性
		seckillGoods.setGoodsId(goods.getTbGoods().getId()); //'spu ID'
		seckillGoods.setItemId(goods.getTbItem().getId());//`item_id`
		seckillGoods.setTitle(goods.getTbItem().getTitle());//'标题',
		seckillGoods.setSmallPic(goods.getTbItem().getImage());//'商品图片',
		seckillGoods.setPrice(goods.getTbItem().getPrice());//''原价格',
		//seckillGoods.setCostPrice(goods.getTbSeckillGoods().getCostPrice());//'秒杀价格',
		seckillGoods.setSellerId(goods.getTbItem().getSellerId());// '商家ID',
		seckillGoods.setCreateTime(new Date()); //秒杀商品的添加的日期自己添加
		seckillGoods.setCheckTime(new Date());//审核日期添加可以不写
		seckillGoods.setStatus("0");   //默认的的默认草稿的
		//seckillGoods.setStartTime(goods.getTbSeckillGoods().getStartTime());
		//seckillGoods.setEndTime(goods.getTbSeckillGoods().getEndTime());
		//seckillGoods.setNum(goods.getTbSeckillGoods().getNum());
		seckillGoods.setStockCount(goods.getTbItem().getNum());//剩余库存数
		//seckillGoods.setIntroduction(goods.getTbSeckillGoods().getIntroduction());

		//将秒杀商品添加到数据库
		seckillGoodsMapper.insert(seckillGoods);

	}


	
	/**
	 * 修改
	 */
	@Override
	public void update(TbSeckillGoods seckillGoods){
		seckillGoodsMapper.updateByPrimaryKey(seckillGoods);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbSeckillGoods findOne(Long id){
		//从redis中获取秒杀商品
		//return seckillGoodsMapper.selectByPrimaryKey(id);
		return (TbSeckillGoods) redisTemplate.boundHashOps("seckill_goods").get(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			seckillGoodsMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSeckillGoods seckillGoods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSeckillGoodsExample example=new TbSeckillGoodsExample();
		Criteria criteria = example.createCriteria();
		
		if(seckillGoods!=null){			
						if(seckillGoods.getTitle()!=null && seckillGoods.getTitle().length()>0){
				criteria.andTitleLike("%"+seckillGoods.getTitle()+"%");
			}
			if(seckillGoods.getSmallPic()!=null && seckillGoods.getSmallPic().length()>0){
				criteria.andSmallPicLike("%"+seckillGoods.getSmallPic()+"%");
			}
			if(seckillGoods.getSellerId()!=null && seckillGoods.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+seckillGoods.getSellerId()+"%");
			}
			if(seckillGoods.getStatus()!=null && seckillGoods.getStatus().length()>0){
				criteria.andStatusLike("%"+seckillGoods.getStatus()+"%");
			}
			if(seckillGoods.getIntroduction()!=null && seckillGoods.getIntroduction().length()>0){
				criteria.andIntroductionLike("%"+seckillGoods.getIntroduction()+"%");
			}
	
		}
		
		Page<TbSeckillGoods> page= (Page<TbSeckillGoods>)seckillGoodsMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}


	/**
	 * 商家对商品的提交的审核 修改状态
	 */

	@Override
	public void updateStatus(Long [] ids, String status) {
		for (Long id : ids) {
		    //根据id查询出每个对象的
            TbSeckillGoods goods = seckillGoodsMapper.selectByPrimaryKey(id);
            //修改商品状态
            goods.setStatus(status);
            seckillGoodsMapper.updateByPrimaryKey(goods);
        }
	}

}
