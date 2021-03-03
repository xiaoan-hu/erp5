package ltd.yuhan.erp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class WarehouseOut {
    private Integer id;

    private Integer orderid;

    private Integer goodsid;

    private Integer qty;

    private Date createtime;

}