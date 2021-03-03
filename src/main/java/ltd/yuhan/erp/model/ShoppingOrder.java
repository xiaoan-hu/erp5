package ltd.yuhan.erp.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Setter
@Getter
public class ShoppingOrder {
    private Integer id;

    private String shopper;

    private Integer totalqty;

    private BigDecimal totalprice;

    private Integer status;

    private String createby;

    private Date createtime;


}