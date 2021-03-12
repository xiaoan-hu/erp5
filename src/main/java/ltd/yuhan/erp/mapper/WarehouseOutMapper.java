package ltd.yuhan.erp.mapper;

import java.util.List;


import ltd.yuhan.erp.model.WarehouseOut;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface WarehouseOutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WarehouseOut record);

    WarehouseOut selectByPrimaryKey(Integer id);

    List<WarehouseOut> selectAll();

    int updateByPrimaryKey(WarehouseOut record);

    List<WarehouseOut> selectByGoodsId(long goodsId);
}