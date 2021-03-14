package ltd.yuhan.erp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastedittime;


}