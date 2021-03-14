package ltd.yuhan.erp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class WarehouseOut {
    private Integer id;

    private Long orderid;

    private Long goodsid;

    private Integer qty;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createtime;

    private Integer status;

}