package ltd.yuhan.erp.mapper;

import ltd.yuhan.erp.model.ShoppingOrder;
import ltd.yuhan.erp.model.Warehouseoutinfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
@Mapper
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