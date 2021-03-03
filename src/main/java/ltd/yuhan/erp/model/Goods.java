package ltd.yuhan.erp.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Goods {
    private Integer id;

    private String title;

    private String picture;

    private BigDecimal price;

    private String introduction;

    private Integer categoryid;

    private String lasteditby;

    private Date lastedittime;

    private BigDecimal longer;

    private BigDecimal wide;

    private BigDecimal high;

    private BigDecimal weight;


}