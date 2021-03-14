package ltd.yuhan.erp.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class Goods {
    private Long id;

    private String title;

    private String picture;

    private BigDecimal price;

    private String introduction;

    private Integer categoryid;

    private String lasteditby;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date lastedittime;

    private BigDecimal longer;

    private BigDecimal wide;

    private BigDecimal high;

    private BigDecimal weight;


}