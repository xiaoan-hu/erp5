package ltd.yuhan.erp.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class Po {
    private Integer id;

    private String orderId;

    private String user;

    private Integer totalqty;

    private BigDecimal totalprice;

    private Integer status;

    private String createby;

    private Date createtime;

    public Po(){
        this.totalprice = BigDecimal.ZERO;
        this.totalqty = 0;
    }

}