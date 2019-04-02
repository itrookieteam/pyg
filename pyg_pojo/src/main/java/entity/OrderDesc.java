package entity;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbOrder;
import com.pinyougou.pojo.TbOrderItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @ClassName OrderDesc
 * @Description TODO
 * @Author lwz
 * @Date 16:25
 * @Version 1.0
 */

public class OrderDesc implements Serializable {

    private String goodsName;//商品名称
    private BigDecimal price;//商品价格
    private Integer num;//商品数量
    private BigDecimal totalFee;//商品总金额
    private String sourceType;//订单来源
    private Date createTtime;//创建时间
    private String status;//状态

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(BigDecimal totalFee) {
        this.totalFee = totalFee;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Date getCreateTtime() {
        return createTtime;
    }

    public void setCreateTtime(Date createTtime) {
        this.createTtime = createTtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
