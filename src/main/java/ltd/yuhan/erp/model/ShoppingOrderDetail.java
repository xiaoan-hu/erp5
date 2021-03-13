package ltd.yuhan.erp.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Getter
@Setter
public class ShoppingOrderDetail {
    private Integer id;

    private Long shoppingorderid;

    private Long goodsid;

    private Integer qty;

    private BigDecimal totalprice;

    private Date createtime;

}