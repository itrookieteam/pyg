package com.pinyougou.sellergoods.service.impl;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import entity.Goods;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.pojo.TbGoodsExample.Criteria;
import com.pinyougou.sellergoods.service.GoodsService;

import entity.PageResult;
import org.springframework.transaction.annotation.Transactional;

import javax.jws.Oneway;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
@Transactional
public class GoodsServiceImpl implements GoodsService {

	@Autowired
	private TbGoodsMapper goodsMapper;

	@Autowired
	private TbGoodsDescMapper goodsDescMapper;

	@Autowired
	private TbItemMapper itemMapper;

	@Autowired
	private TbItemCatMapper itemCatMapper;

	@Autowired
	private TbBrandMapper brandMapper;

	@Autowired
	private TbSellerMapper sellerMapper;




	@Autowired
	private TbGoodsMapper tbGoodsMapper;



	/**
	 * 查询全部
	 */
	@Override
	public List<TbGoods> findAll() {
		return goodsMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbGoods> page=   (Page<TbGoods>) goodsMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(Goods goods) {
		//封装对象中获取商品对象保存数据库
		TbGoods tbGoods = goods.getGoods();
		tbGoods.setAuditStatus("0"); //新增商品默认为草稿状态
		goodsMapper.insert(tbGoods);

		TbGoodsDesc goodsDesc = goods.getGoodsDesc();
		//注意一对一关系的维护
		goodsDesc.setGoodsId(tbGoods.getId()); //注意dao层新增后获取新增对象的id需要配置xml文件
		goodsDescMapper.insert(goodsDesc);

//		int i = 1/0;
		this.addItem(goods);
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(Goods goods){
		//获取商品对象
		TbGoods tbGoods = goods.getGoods();
		tbGoods.setAuditStatus("0"); //新增商品默认为草稿状态
		goodsMapper.updateByPrimaryKey(tbGoods);//修改商品对象

		//获取商品Desc对象
		TbGoodsDesc goodsDesc = goods.getGoodsDesc();
//		System.out.println(tbGoods.getIsEnableSpec());
		if("0".equals(goods.getGoods().getIsEnableSpec())){
			goodsDesc.setSpecificationItems("[]");
		}
		goodsDescMapper.updateByPrimaryKey(goodsDesc);//更改

		//商品item表：将当前商品的全部item删除，重新添加
		TbItemExample example = new TbItemExample();
		example.createCriteria().andGoodsIdEqualTo(tbGoods.getId());
		itemMapper.deleteByExample(example);

		//重新添加
		this.addItem(goods);
	}

	//商品item添加
	public void addItem (Goods goods ){
		TbGoods tbGoods = goods.getGoods();
		TbGoodsDesc goodsDesc = goods.getGoodsDesc();
		if("1".equals(tbGoods.getIsEnableSpec())){
			//处理sku列表集合
			List<TbItem> itemList = goods.getItemList();
			for (TbItem item : itemList) {
				//price,num,isDefault,spec,status 页面传入的值
				//title是商品名称+spec中的所有value值
				String title = tbGoods.getGoodsName();
				Map<String,String> map = JSON.parseObject(item.getSpec(), Map.class);
				//循环spec中的值{"机身内存":"16G","网络":"联通3G"}
				for (String s : map.keySet()) {
					title += " " +map.get(s);
				}
				item.setTitle(title);

				//商品卖点
				item.setSellPoint(tbGoods.getCaption());
				//保存商品图片集合中的图片地址，取第一张
				List<Map> images = JSON.parseArray(goodsDesc.getItemImages(), Map.class);
				if(images.size() > 0){
					//取第一个对象的图片地址
					item.setImage(images.get(0).get("url").toString());
				}

				//需要保存三级分类id
				item.setCategoryid(tbGoods.getCategory3Id());
				item.setCreateTime(new Date());
				item.setUpdateTime(new Date());
				item.setGoodsId(tbGoods.getId());

				//设置商家的id
				item.setSellerId(tbGoods.getSellerId()); //注意确认已经从springSecurity中获取到sellerid封装到goods中


				//设置分类名称
				TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id());
				item.setCategory(itemCat.getName());

				//设置品牌名称
				TbBrand brand = brandMapper.selectByPrimaryKey(tbGoods.getBrandId());
				item.setBrand(brand.getName());

				//设置seller的name
				TbSeller seller = sellerMapper.selectByPrimaryKey(tbGoods.getSellerId());
				item.setSeller(seller.getName());

				//注意传入的status和isDefault的长度！！！！
				itemMapper.insert(item);
			}
		}else{
			//没启用规格
			TbItem item = new TbItem();
			item.setTitle(tbGoods.getGoodsName()); // 商品名称直接设置到sku对象上


			//商品卖点
			item.setSellPoint(tbGoods.getCaption());
			//保存商品图片集合中的图片地址，取第一张
			List<Map> images = JSON.parseArray(goodsDesc.getItemImages(), Map.class);
			if(images.size() > 0){
				//取第一个对象的图片地址
				item.setImage(images.get(0).get("url").toString());
			}

			//需要保存三级分类id
			item.setCategoryid(tbGoods.getCategory3Id());
			item.setCreateTime(new Date());
			item.setUpdateTime(new Date());
			item.setGoodsId(tbGoods.getId());

			//设置商家的id
			item.setSellerId(tbGoods.getSellerId()); //注意确认已经从springSecurity中获取到sellerid封装到goods中


			//设置分类名称
			TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id());
			item.setCategory(itemCat.getName());

			//设置品牌名称
			TbBrand brand = brandMapper.selectByPrimaryKey(tbGoods.getBrandId());
			item.setBrand(brand.getName());

			//设置seller的name
			TbSeller seller = sellerMapper.selectByPrimaryKey(tbGoods.getSellerId());
			item.setSeller(seller.getName());


			//将spec，status，isDefault，price，num设置默认值（之前是页面传入）
			item.setPrice(tbGoods.getPrice());
			item.setStatus("1");
			item.setIsDefault("1");
			item.setNum(99999);

			itemMapper.insert(item);

		}
	}
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public Goods findOne(Long id){
		//封装goods对象
		Goods goods = new Goods();
		goods.setGoods(goodsMapper.selectByPrimaryKey(id));

		//封装goodsDesc对象
		goods.setGoodsDesc(goodsDescMapper.selectByPrimaryKey(id));

		//封装itemList列表
		TbItemExample example = new TbItemExample();
		example.createCriteria().andGoodsIdEqualTo(id);
		List<TbItem> items = itemMapper.selectByExample(example);
		goods.setItemList(items);

		return goods;
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Long[] ids) {
		for(Long id:ids){
			//物理删除！改成逻辑删除
			//goodsMapper.deleteByPrimaryKey(id);
			TbGoods goods = goodsMapper.selectByPrimaryKey(id);
			goods.setIsDelete("1");
			goodsMapper.updateByPrimaryKey(goods);
			//逻辑删除商品规格
			TbItemExample example = new TbItemExample();
			example.createCriteria().andGoodsIdEqualTo(id);
			//根据商品id获取所有规格
			List<TbItem> tbItems = itemMapper.selectByExample(example);
			//遍历规格，更改状态为3
			for (TbItem tbItem : tbItems) {
				tbItem.setStatus("3");
				itemMapper.updateByPrimaryKey(tbItem);
			}
		}		
	}
	
	
		@Override
	public PageResult findPage(TbGoods goods, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbGoodsExample example=new TbGoodsExample();
		Criteria criteria = example.createCriteria();

		//只查询未删除的商品
		criteria.andIsDeleteIsNull();

		if(goods!=null){
			if(goods.getSellerId()!=null && goods.getSellerId().length()>0){  //商家id查询
				criteria.andSellerIdEqualTo(goods.getSellerId());
			}
			if(goods.getGoodsName()!=null && goods.getGoodsName().length()>0){  //名称查询
				criteria.andGoodsNameLike("%"+goods.getGoodsName()+"%");
			}
			if(goods.getAuditStatus()!=null && goods.getAuditStatus().length()>0){ //状态查询
				criteria.andAuditStatusEqualTo(goods.getAuditStatus());
			}
		}
		
		Page<TbGoods> page= (Page<TbGoods>)goodsMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void updateStatus(Long[] ids, String status) {
		for (Long id : ids) {
			//将商品状态提交到数据库
			TbGoods goods = goodsMapper.selectByPrimaryKey(id);
			goods.setAuditStatus(status);
			goodsMapper.updateByPrimaryKey(goods);
			//查询商品下的所有规格列表
			TbItemExample example = new TbItemExample();
			example.createCriteria().andGoodsIdEqualTo(goods.getId());
			List<TbItem> tbItems = itemMapper.selectByExample(example);
			//如果商品上架，将规格状态改为正常
			if("5".equals(status)){
				for (TbItem tbItem : tbItems) {
					tbItem.setStatus("1");
					itemMapper.updateByPrimaryKey(tbItem);
				}
			}
			//如果商品下架，将商品规格改为下架2
			if("6".equals(status)){
				for (TbItem tbItem : tbItems) {
					tbItem.setStatus("2");
					itemMapper.updateByPrimaryKey(tbItem);
				}
			}
		}
	}


	// 根据商家查询所有商品的信息
	@Override
	public List<TbGoods> findgoodsList(String sellerId) {
		TbGoodsExample example =  new TbGoodsExample();
        example.createCriteria().andSellerIdEqualTo(sellerId);
		List<TbGoods> tbGoods = tbGoodsMapper.selectByExample(example);
		return tbGoods;
	}



}
