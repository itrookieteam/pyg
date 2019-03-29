package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import entity.PageResult;
import entity.Result;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

	@Reference
	private SellerService sellerService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbSeller> findAll(){			
		return sellerService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return sellerService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param seller
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbSeller seller){
		try {
			sellerService.add(seller);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param seller
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbSeller seller){
		try {
			sellerService.update(seller);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbSeller findOne(String id){
		return sellerService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(String [] ids){
		try {
			sellerService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbSeller seller, int page, int rows  ){
		return sellerService.findPage(seller, page, rows);		
	}

	/**
	 * 运营商审核商家
	 * @param sellerId
	 * @param status
	 * @return
	 */
	@RequestMapping("/updateStatus")
	public Result updateStatus(String sellerId,String status){
		try {
			sellerService.updateStatus(sellerId, status);
			return new Result(true, "审核成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "审核失败");
		}
	}

	@RequestMapping("/exportExcel")
	public Result exportExcel(){
		try {
			sellerService.exportExcel();
			return new Result(true,"导出成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false,"导出失败");
		}
	}

    @RequestMapping("/importExcel")
    public Result importExcel(MultipartFile file){
        try {
        	//获取文件名
			String filename = file.getOriginalFilename();
			//结果文件后缀
			String houzui = filename.substring(filename.lastIndexOf("."));//包含当前索引
			System.out.println("fileName + " + filename + " + " + houzui);
			//判断后缀名
			if(".xls".equals(houzui) || ".xlsx".equals(houzui)){
				//获取文件流对象
				InputStream inputStream = file.getInputStream();
				//获取工作薄
				HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
				//获取工作表
				HSSFSheet sheet = workbook.getSheetAt(0);
				//循环获取表格数据
				for (Row row : sheet) {
					if(row.getRowNum() == 0){
						continue;
					}
					String value = row.getCell(0).getStringCellValue();
					System.out.println(value);
				}
				return new Result(true,"导入成功");
			}else{
				return new Result(false,"请选择正确的Excel文件");
			}
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,"导入失败");
        }
    }

}
