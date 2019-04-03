package com.pinyougou.manager.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbSeller;
import com.pinyougou.sellergoods.service.SellerService;
import entity.PageResult;
import entity.Result;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * controller
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/seller")
public class SellerController {

    @Reference
    private SellerService sellerService;

    private SimpleDateFormat format = new SimpleDateFormat();

    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findAll")
    public List<TbSeller> findAll() {
        return sellerService.findAll();
    }


    /**
     * 返回全部列表
     *
     * @return
     */
    @RequestMapping("/findPage")
    public PageResult findPage(int page, int rows) {
        return sellerService.findPage(page, rows);
    }

    /**
     * 增加
     *
     * @param seller
     * @return
     */
    @RequestMapping("/add")
    public Result add(@RequestBody TbSeller seller) {
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
     *
     * @param seller
     * @return
     */
    @RequestMapping("/update")
    public Result update(@RequestBody TbSeller seller) {
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
     *
     * @param id
     * @return
     */
    @RequestMapping("/findOne")
    public TbSeller findOne(String id) {
        return sellerService.findOne(id);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("/delete")
    public Result delete(String[] ids) {
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
     *
     * @param
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/search")
    public PageResult search(@RequestBody TbSeller seller, int page, int rows) {
        return sellerService.findPage(seller, page, rows);
    }

    /**
     * 运营商审核商家
     *
     * @param sellerId
     * @param status
     * @return
     */
    @RequestMapping("/updateStatus")
    public Result updateStatus(String sellerId, String status) {
        try {
            sellerService.updateStatus(sellerId, status);
            return new Result(true, "审核成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "审核失败");
        }
    }

    @RequestMapping("/exportExcel")
    public Result exportExcel() {
        try {
            sellerService.exportExcel();
            return new Result(true, "导出成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "导出失败");
        }
    }

    @RequestMapping("/importExcel")
    public Result importExcel(MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                String filePath = file.getOriginalFilename();
                InputStream is = file.getInputStream();

                Workbook hssfWorkbook = null;
                if (filePath.endsWith("xlsx")) {
                    hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
                } else if (filePath.endsWith("xls")) {
                    hssfWorkbook = new HSSFWorkbook(is);//Excel 2003
                }

                TbSeller seller = null;
                List<TbSeller> list = new ArrayList<>();
                Sheet hssfSheet = hssfWorkbook.getSheetAt(0);
                // 循环行Row
                for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                    Row hssfRow = hssfSheet.getRow(rowNum);
                    if (hssfRow != null) {
                        seller = new TbSeller();
                        Cell sellerId = hssfRow.getCell(0);
                        Cell name = hssfRow.getCell(1);
                        Cell nickName = hssfRow.getCell(2);
                        Cell password = hssfRow.getCell(3);
                        Cell email = hssfRow.getCell(4);
                        Cell mobile = hssfRow.getCell(5);
                        Cell telephone = hssfRow.getCell(6);
                        Cell status = hssfRow.getCell(7);
                        Cell addressDetail = hssfRow.getCell(8);
                        Cell linkmanName = hssfRow.getCell(9);
                        Cell linkmanQq = hssfRow.getCell(10);
                        Cell linkmanMobile = hssfRow.getCell(11);
                        Cell linkmanEmail = hssfRow.getCell(12);
                        Cell licenseNumber = hssfRow.getCell(13);
                        Cell taxNumber = hssfRow.getCell(14);
                        Cell orgNumber = hssfRow.getCell(15);
                        Cell address = hssfRow.getCell(16);
                        Cell logoPic = hssfRow.getCell(17);
                        Cell brief = hssfRow.getCell(18);
                        Cell createTime = hssfRow.getCell(19);
                        Cell legalPerson = hssfRow.getCell(20);
                        Cell legalPersonCardId = hssfRow.getCell(21);
                        Cell bankUser = hssfRow.getCell(22);
                        Cell bankName = hssfRow.getCell(23);
                        //这里是自己的逻辑
                        seller.setSellerId(sellerId.toString());
                        seller.setName(name.toString());
                        seller.setNickName(nickName.toString());
                        seller.setPassword(password.toString());
                        seller.setEmail(email.toString());
                        seller.setMobile(mobile.toString());
                        seller.setTelephone(telephone.toString());
                        seller.setStatus(status.toString());
                        seller.setAddressDetail(addressDetail.toString());
                        seller.setLinkmanName(linkmanName.toString());
                        seller.setLinkmanQq(linkmanQq.toString());
                        seller.setLinkmanMobile(linkmanMobile.toString());
                        seller.setLinkmanEmail(linkmanEmail.toString());
                        seller.setLicenseNumber(licenseNumber.toString());
                        seller.setTaxNumber(taxNumber.toString());
                        seller.setOrgNumber(orgNumber.toString());
                        seller.setAddress((long) Double.parseDouble(address.toString()));
                        seller.setLogoPic(logoPic.toString());
                        seller.setBrief(brief.toString());
                        Date date = HSSFDateUtil.getJavaDate(createTime.getNumericCellValue());
                        seller.setCreateTime(date);
                        seller.setLegalPerson(legalPerson.toString());
                        seller.setLegalPersonCardId(legalPersonCardId.toString());
                        seller.setBankUser(bankUser.toString());
                        seller.setBankName(bankName.toString());

                        list.add(seller);
                    }
                }
                sellerService.inportExcel(list);
                return new Result(true, "导入成功");
            }
            return new Result(false, "导入失败");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "导入失败");
        }
    }

    //上传Excel
    public boolean uploadExcel(MultipartFile file, HttpServletRequest request) throws IOException {

        if (!file.isEmpty()) {
            String filePath = file.getOriginalFilename();
            //windows
            String savePath = request.getSession().getServletContext().getRealPath(filePath);

            File targetFile = new File(savePath);

            if (!targetFile.exists()) {
                targetFile.mkdirs();
            }

            file.transferTo(targetFile);
            return true;
        }

        return false;
    }

    public void readExcel(String fileName) throws Exception {
        InputStream is = new FileInputStream(new File(fileName));
        Workbook hssfWorkbook = null;
        if (fileName.endsWith("xlsx")) {
            hssfWorkbook = new XSSFWorkbook(is);//Excel 2007
        } else if (fileName.endsWith("xls")) {
            hssfWorkbook = new HSSFWorkbook(is);//Excel 2003

        }
        // HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
        // XSSFWorkbook hssfWorkbook = new XSSFWorkbook(is);
        TbSeller seller = null;
        List<TbSeller> list = new ArrayList<>();
        // 循环工作表Sheet
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
            //HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            Sheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
            if (hssfSheet == null) {
                continue;
            }
            // 循环行Row
            for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
                //HSSFRow hssfRow = hssfSheet.getRow(rowNum);
                Row hssfRow = hssfSheet.getRow(rowNum);
                if (hssfRow != null) {
                    seller = new TbSeller();
                    //HSSFCell name = hssfRow.getCell(0);
                    //HSSFCell pwd = hssfRow.getCell(1);
                    Cell name = hssfRow.getCell(0);
                    Cell pwd = hssfRow.getCell(1);
//这里是自己的逻辑
//					student.setUserName(name.toString());
//					student.setPassword(pwd.toString());

                    list.add(seller);
                }
            }
        }


    }

}
