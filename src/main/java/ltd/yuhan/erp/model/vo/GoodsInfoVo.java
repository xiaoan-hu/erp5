package ltd.yuhan.erp.model.vo;

import lombok.Getter;
import lombok.Setter;
import ltd.yuhan.erp.model.Goods;

import java.math.BigDecimal;
import java.util.Date;
@Setter
@Getter
public class GoodsInfoVo {
    private long id;
    private String title;
    private BigDecimal price;
    private String introduction;
    private Integer categoryid;
    private Integer qty;
    private Integer intrans;

    public GoodsInfoVo(){}

    public GoodsInfoVo(Goods good){
        this.id = good.getId();
        this.title = good.getTitle();
        this.price = good.getPrice();
        this.introduction = good.getIntroduction();
        this.categoryid = good.getCategoryid();
    }

}
