package ltd.yuhan.erp.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import ltd.yuhan.erp.model.PoDetail;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class PoDetailVo {
    private Integer id;
    private String orderId;
    private Integer poid;
    private String goodTitle;
    private long goodsid;
    private Integer qty;
    private BigDecimal totalprice;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createtime;
    public PoDetailVo(){}
    public PoDetailVo(PoDetail pd){
        this.id=pd.getId();
        this.poid=pd.getPoid();
        this.goodsid=pd.getGoodsid();
        this.qty=pd.getQty();
        this.totalprice=pd.getTotalprice();
        this.createtime=pd.getCreatetime();
    }
}
