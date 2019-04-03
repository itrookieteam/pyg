package entity;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.pojo.TbItem;
import com.pinyougou.pojo.TbSeckillGoods;
import sun.dc.pr.PRError;

import java.io.Serializable;

public class SeckillGoods implements Serializable {

    private TbGoods tbGoods;
    private TbItem tbItem;
    private TbSeckillGoods tbSeckillGoods;


    public TbGoods getTbGoods() {
        return tbGoods;
    }

    public void setTbGoods(TbGoods tbGoods) {
        this.tbGoods = tbGoods;
    }

    public TbItem getTbItem() {
        return tbItem;
    }

    public void setTbItem(TbItem tbItem) {
        this.tbItem = tbItem;
    }

    public TbSeckillGoods getTbSeckillGoods() {
        return tbSeckillGoods;
    }

    public void setTbSeckillGoods(TbSeckillGoods tbSeckillGoods) {
        this.tbSeckillGoods = tbSeckillGoods;
    }
}
