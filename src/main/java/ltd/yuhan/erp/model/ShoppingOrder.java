package ltd.yuhan.erp.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
@Setter
@Getter
public class ShoppingOrder {
    //TODO 订单ID要改成long,相关mapper需要修改
    private Long id;

    private String shopper;

    private Integer totalqty;

    private BigDecimal totalprice;

    private Integer status;

    private String createby;

    private Date createtime;


}