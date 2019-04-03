package com.pinyougou.sellergoods.service.impl;
import java.util.ArrayList;
import java.util.List;

import com.pinyougou.mapper.TbGoodsMapper;
import com.pinyougou.mapper.TbOrderMapper;
import com.pinyougou.pojo.*;
import entity.OrderDesc;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbOrderItemMapper;
import com.pinyougou.pojo.TbOrderItemExample.Criteria;
import com.pinyougou.sellergoods.service.OrderItemService;

import entity.PageResult;


/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbGoodsMapper tbGoodsMapper;
	@Autowired
	private TbOrderMapper tbOrderMapper;
	@Autowired
	private TbOrderItemMapper tbOrderItemMapper;
	/**
	 * 查询全部
	 */
	@Override
	public List<TbOrderItem> findAll() {
		return orderItemMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbOrderItem> page=   (Page<TbOrderItem>) orderItemMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbOrderItem orderItem) {
		orderItemMapper.insert(orderItem);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbOrderItem orderItem){
		orderItemMapper.updateByPrimaryKey(orderItem);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbOrderItem findOne(Long id){
		return orderItemMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			orderItemMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbOrderItem orderItem, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbOrderItemExample example=new TbOrderItemExample();
		Criteria criteria = example.createCriteria();
		
		if(orderItem!=null){			
						if(orderItem.getTitle()!=null && orderItem.getTitle().length()>0){
				criteria.andTitleLike("%"+orderItem.getTitle()+"%");
			}
			if(orderItem.getPicPath()!=null && orderItem.getPicPath().length()>0){
				criteria.andPicPathLike("%"+orderItem.getPicPath()+"%");
			}
			if(orderItem.getSellerId()!=null && orderItem.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+orderItem.getSellerId()+"%");
			}
	
		}
		
		Page<TbOrderItem> page= (Page<TbOrderItem>)orderItemMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public List<OrderDesc> findBySellerId(String sellerId) {
        //根据sellerId查询订单表所有信息
		List<OrderDesc> orderDescs = new ArrayList<>();
		//添加条件
		TbOrderExample tbOrderExample = new TbOrderExample();
		tbOrderExample.createCriteria().andSellerIdEqualTo(sellerId);
		List<TbOrder> orders = tbOrderMapper.selectByExample(tbOrderExample);

		for (TbOrder tbOrder : orders) {
			OrderDesc orderDesc = new OrderDesc();
			orderDesc.setSourceType(tbOrder.getSourceType());
			orderDesc.setCreateTime(tbOrder.getCreateTime());
			orderDesc.setStatus(tbOrder.getStatus());
			//根据订单id查询订单分类表数据
			TbOrderItemExample tbOrderItemExample = new TbOrderItemExample();
			tbOrderItemExample.createCriteria().andOrderIdEqualTo(tbOrder.getOrderId());
			List<TbOrderItem> tbOrderItems = tbOrderItemMapper.selectByExample(tbOrderItemExample);
			for (TbOrderItem tbOrderItem : tbOrderItems) {
				orderDesc.setPrice(tbOrderItem.getPrice());
				orderDesc.setNum(tbOrderItem.getNum());
				orderDesc.setTotalFee(tbOrderItem.getTotalFee());
				//根据订单分类表的goodsId查询商品名称
				TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(tbOrderItem.getGoodsId());
				orderDesc.setGoodsName(tbGoods.getGoodsName());
				orderDescs.add(orderDesc);
			}

		}
		return orderDescs;
	}
}
