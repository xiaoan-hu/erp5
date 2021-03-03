package ltd.yuhan.erp.mapper;

import java.util.List;
import ltd.yuhan.erp.model.ShoppingOrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShoppingOrderDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ShoppingOrderDetail record);

    ShoppingOrderDetail selectByPrimaryKey(Integer id);

    List<ShoppingOrderDetail> selectAll();

    int updateByPrimaryKey(ShoppingOrderDetail record);
}