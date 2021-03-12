package ltd.yuhan.erp.model.vo;

import lombok.Getter;
import lombok.Setter;
import ltd.yuhan.erp.model.Po;
import ltd.yuhan.erp.model.PoDetail;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class PoVo {
    private Integer id;
    private String  orderId;
    private String user;
    private Integer totalqty;
    private BigDecimal totalprice;
    private Integer status;
    private String createby;
    private Date createtime;
    private List<Map> poDetails;
    public PoVo(){}
    public PoVo(Po po){
        this.id=po.getId();
        this.orderId=po.getOrderId();
        this.user=po.getUser();
        this.totalqty=po.getTotalqty();
        this.totalprice=po.getTotalprice();
        this.status=po.getStatus();
        this.createby=po.getCreateby();
        this.createtime=po.getCreatetime();
    }

}