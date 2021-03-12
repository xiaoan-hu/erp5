package ltd.yuhan.erp.mapper;

import java.util.List;
import ltd.yuhan.erp.model.GoodsCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
@Component
@Mapper
public interface GoodsCategoryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(GoodsCategory record);

    GoodsCategory selectByPrimaryKey(Integer id);

    List<GoodsCategory> selectAll();

    int updateByPrimaryKey(GoodsCategory record);
}