package ltd.yuhan.erp.mapper;

import ltd.yuhan.erp.model.WarehouseOut;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@Mapper
public interface WarehouseOutMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(WarehouseOut record);

    WarehouseOut selectByPrimaryKey(Integer id);

    List<WarehouseOut> selectAll();

    int updateByPrimaryKey(WarehouseOut record);

    List<WarehouseOut> selectByGoodsId(long goodsId);
    //查找一个order,某种商品已有的发货单
    List<WarehouseOut> getWarehouseOutByTerm(Long goodsId, Long orderId);
    //根据infoid查找发货单
    List<Map> getWarehouseOutByInfoid(Long infoId);



}