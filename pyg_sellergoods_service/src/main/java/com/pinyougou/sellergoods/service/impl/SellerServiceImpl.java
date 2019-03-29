package com.pinyougou.sellergoods.service.impl;
import java.io.*;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.TbSellerMapper;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.pojo.TbSellerExample;
import com.pinyougou.pojo.TbSellerExample.Criteria;
import com.pinyougou.sellergoods.service.SellerService;

import entity.PageResult;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class SellerServiceImpl implements SellerService {

	@Autowired
	private TbSellerMapper sellerMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbSeller> findAll() {
		return sellerMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbSeller> page=   (Page<TbSeller>) sellerMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbSeller seller) {
		//设置默认值，状态为0
		seller.setStatus("0"); //0：未审核  1：审核通过  2：审核未通过  3：关闭商家
		seller.setCreateTime(new Date()); //创建时间

		//需要对注册用户的密码进行加密处理
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		seller.setPassword(encoder.encode(seller.getPassword()));

		sellerMapper.insert(seller);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbSeller seller){
		sellerMapper.updateByPrimaryKey(seller);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbSeller findOne(String id){
		return sellerMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(String[] ids) {
		for(String id:ids){
			sellerMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbSeller seller, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbSellerExample example=new TbSellerExample();
		Criteria criteria = example.createCriteria();
		
		if(seller!=null){			
						if(seller.getSellerId()!=null && seller.getSellerId().length()>0){
				criteria.andSellerIdLike("%"+seller.getSellerId()+"%");
			}
			if(seller.getName()!=null && seller.getName().length()>0){
				criteria.andNameLike("%"+seller.getName()+"%");
			}
			if(seller.getNickName()!=null && seller.getNickName().length()>0){
				criteria.andNickNameLike("%"+seller.getNickName()+"%");
			}
			if(seller.getPassword()!=null && seller.getPassword().length()>0){
				criteria.andPasswordLike("%"+seller.getPassword()+"%");
			}
			if(seller.getEmail()!=null && seller.getEmail().length()>0){
				criteria.andEmailLike("%"+seller.getEmail()+"%");
			}
			if(seller.getMobile()!=null && seller.getMobile().length()>0){
				criteria.andMobileLike("%"+seller.getMobile()+"%");
			}
			if(seller.getTelephone()!=null && seller.getTelephone().length()>0){
				criteria.andTelephoneLike("%"+seller.getTelephone()+"%");
			}
			if(seller.getStatus()!=null && seller.getStatus().length()>0){
				criteria.andStatusLike("%"+seller.getStatus()+"%");
			}
			if(seller.getAddressDetail()!=null && seller.getAddressDetail().length()>0){
				criteria.andAddressDetailLike("%"+seller.getAddressDetail()+"%");
			}
			if(seller.getLinkmanName()!=null && seller.getLinkmanName().length()>0){
				criteria.andLinkmanNameLike("%"+seller.getLinkmanName()+"%");
			}
			if(seller.getLinkmanQq()!=null && seller.getLinkmanQq().length()>0){
				criteria.andLinkmanQqLike("%"+seller.getLinkmanQq()+"%");
			}
			if(seller.getLinkmanMobile()!=null && seller.getLinkmanMobile().length()>0){
				criteria.andLinkmanMobileLike("%"+seller.getLinkmanMobile()+"%");
			}
			if(seller.getLinkmanEmail()!=null && seller.getLinkmanEmail().length()>0){
				criteria.andLinkmanEmailLike("%"+seller.getLinkmanEmail()+"%");
			}
			if(seller.getLicenseNumber()!=null && seller.getLicenseNumber().length()>0){
				criteria.andLicenseNumberLike("%"+seller.getLicenseNumber()+"%");
			}
			if(seller.getTaxNumber()!=null && seller.getTaxNumber().length()>0){
				criteria.andTaxNumberLike("%"+seller.getTaxNumber()+"%");
			}
			if(seller.getOrgNumber()!=null && seller.getOrgNumber().length()>0){
				criteria.andOrgNumberLike("%"+seller.getOrgNumber()+"%");
			}
			if(seller.getLogoPic()!=null && seller.getLogoPic().length()>0){
				criteria.andLogoPicLike("%"+seller.getLogoPic()+"%");
			}
			if(seller.getBrief()!=null && seller.getBrief().length()>0){
				criteria.andBriefLike("%"+seller.getBrief()+"%");
			}
			if(seller.getLegalPerson()!=null && seller.getLegalPerson().length()>0){
				criteria.andLegalPersonLike("%"+seller.getLegalPerson()+"%");
			}
			if(seller.getLegalPersonCardId()!=null && seller.getLegalPersonCardId().length()>0){
				criteria.andLegalPersonCardIdLike("%"+seller.getLegalPersonCardId()+"%");
			}
			if(seller.getBankUser()!=null && seller.getBankUser().length()>0){
				criteria.andBankUserLike("%"+seller.getBankUser()+"%");
			}
			if(seller.getBankName()!=null && seller.getBankName().length()>0){
				criteria.andBankNameLike("%"+seller.getBankName()+"%");
			}
	
		}
		
		Page<TbSeller> page= (Page<TbSeller>)sellerMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

	@Override
	public void updateStatus(String sellerId, String status) {
		//获取seller对象
		TbSeller seller = sellerMapper.selectByPrimaryKey(sellerId);
		seller.setStatus(status);
		//更新数据库
		sellerMapper.updateByPrimaryKey(seller);
	}

	/**
	 * 导出Excel
	 */
	@Override
	public void exportExcel() throws Exception {
		//查询数据库，获取所有商家信息
		List<TbSeller> sellerList = sellerMapper.selectByExample(null);
		//创建工作簿
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
		//创建工作表
		HSSFSheet sheet = hssfWorkbook.createSheet();
		//创建标题行
		HSSFRow title_row = sheet.createRow(0);
		//设置标题单元格
		String[] titleStr = {"用户ID","公司名","店铺名称","密码","EMAIL","公司手机","公司电话","状态","详细地址","联系人姓名","联系人QQ"
		,"联系人电话","联系人EMAIL","营业执照号","税务登记证号","组织机构代码","公司地址","公司LOGO图","简介","创建日期","法定代表人"
		,"法定代表人身份证","开户行账号名称","开户行"
		};
		for (int i = 0; i < titleStr.length; i++) {
			title_row.createCell(i).setCellValue(titleStr[i]);
		}

		//内容设置
		for (int i = 0; i < sellerList.size(); i++) {
			//创建行
			HSSFRow row = sheet.createRow(i+1);
			//获取seller对象
			TbSeller seller = sellerList.get(i);
			row.createCell(0).setCellValue(seller.getSellerId());
			row.createCell(1).setCellValue(seller.getName());
			row.createCell(2).setCellValue(seller.getNickName());
			row.createCell(3).setCellValue(seller.getPassword());
			row.createCell(4).setCellValue(seller.getEmail());
			row.createCell(5).setCellValue(seller.getMobile());
			row.createCell(6).setCellValue(seller.getTelephone());
			row.createCell(7).setCellValue(seller.getStatus());
			row.createCell(8).setCellValue(seller.getAddressDetail());
			row.createCell(9).setCellValue(seller.getLinkmanName());
			row.createCell(10).setCellValue(seller.getLinkmanQq());
			row.createCell(11).setCellValue(seller.getLinkmanMobile());
			row.createCell(12).setCellValue(seller.getLinkmanEmail());
			row.createCell(13).setCellValue(seller.getLicenseNumber());
			row.createCell(14).setCellValue(seller.getTaxNumber());
			row.createCell(15).setCellValue(seller.getOrgNumber());
			if(seller.getAddress() == null||seller.getAddress().equals("")){
				row.createCell(16).setCellValue(0);
			}else{
				row.createCell(16).setCellValue(seller.getAddress());
			}
			row.createCell(17).setCellValue(seller.getLogoPic());
			row.createCell(18).setCellValue(seller.getBrief());
			if(seller.getCreateTime() == null||seller.getCreateTime().equals("")){
				row.createCell(16).setCellValue("");
			}else{
				row.createCell(19).setCellValue(seller.getCreateTime());
			}
			row.createCell(20).setCellValue(seller.getLegalPerson());
			row.createCell(21).setCellValue(seller.getLegalPersonCardId());
			row.createCell(22).setCellValue(seller.getBankUser());
			row.createCell(23).setCellValue(seller.getBankName());
		}

		//流输出
		OutputStream os = new FileOutputStream("D:\\img\\测试代码.xls");
		hssfWorkbook.write(os);
		os.close();
	}


}
