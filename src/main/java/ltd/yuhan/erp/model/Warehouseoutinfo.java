package ltd.yuhan.erp.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * WarehouseOutInfo
 * @author 
 */

@Getter
@Setter
public class Warehouseoutinfo implements Serializable {
    private Integer outinfoid;

    /**
     * 销货单号
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long orderid;

    private Date createtime;

    /**
     * 0代表生成出库单，但未发货状态，1为已发货状态
     */
    private Integer status;

    private static final long serialVersionUID = 1L;
}