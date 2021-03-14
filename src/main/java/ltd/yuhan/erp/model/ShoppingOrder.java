package ltd.yuhan.erp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createtime;


}