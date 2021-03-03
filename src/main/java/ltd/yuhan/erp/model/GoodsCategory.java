package ltd.yuhan.erp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Setter
@Getter
public class GoodsCategory {
    private Integer id;

    private String categoryname;

    private Integer goodscount;

    private Integer serialno;

    private String lasteditby;

    private Date lastedittime;


}