package ltd.yuhan.erp.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PoDetail {
    private Integer id;

    private Integer poid;

    private long goodsid;

    private Integer qty;

    private BigDecimal totalprice;

    private Date createtime;

    public PoDetail(){
        this.totalprice = BigDecimal.ZERO;
        this.qty = 0;
    }

}