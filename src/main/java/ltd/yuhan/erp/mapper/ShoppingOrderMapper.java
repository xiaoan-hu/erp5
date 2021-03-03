package ltd.yuhan.erp.mapper;

import java.util.List;
import ltd.yuhan.erp.model.ShoppingOrder;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShoppingOrder record);

    ShoppingOrder selectByPrimaryKey(Integer id);

    List<ShoppingOrder> selectAll();

    int updateByPrimaryKey(ShoppingOrder record);
}