package ltd.yuhan.erp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createtime;

}