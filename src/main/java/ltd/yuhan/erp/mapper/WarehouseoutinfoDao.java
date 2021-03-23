package ltd.yuhan.erp.mapper;

import ltd.yuhan.erp.model.ShoppingOrder;
import ltd.yuhan.erp.model.Warehouseoutinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface WarehouseoutinfoDao {
    int deleteByPrimaryKey(Integer outinfoid);

    int insert(Warehouseoutinfo record);

    int insertSelective(Warehouseoutinfo record);

    Warehouseoutinfo selectByPrimaryKey(Integer outinfoid);

    int updateByPrimaryKeySelective(Warehouseoutinfo record);

    int updateByPrimaryKey(Warehouseoutinfo record);

    int selectMaxPrimaryKey();

    List<Map> getWarehouseoutinfoByTerm(@Param("status")Integer status);
}