package ltd.yuhan.erp.mapper;

import java.util.List;
import ltd.yuhan.erp.model.WarehouseIn;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WarehouseInMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WarehouseIn record);

    WarehouseIn selectByPrimaryKey(Integer id);

    List<WarehouseIn> selectAll();

    int updateByPrimaryKey(WarehouseIn record);

    List<WarehouseIn> selectByGoodsId(long goodsId);
}