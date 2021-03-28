package ltd.yuhan.erp.mapper;

import java.util.List;

import ltd.yuhan.erp.model.Po;
import ltd.yuhan.erp.model.ShoppingOrder;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface ShoppingOrderMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ShoppingOrder record);

    ShoppingOrder selectByPrimaryKey(Long id);

    List<ShoppingOrder> selectAll();

    int updateByPrimaryKey(ShoppingOrder record);

    List<ShoppingOrder> getShoppingOrderByTerm(String status, String shopper,String goodsTitle, String category,String orderId);
}