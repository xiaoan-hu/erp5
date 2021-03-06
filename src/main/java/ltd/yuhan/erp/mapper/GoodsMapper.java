package ltd.yuhan.erp.mapper;

import java.util.List;
import ltd.yuhan.erp.model.Goods;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
@Component
@Mapper
public interface GoodsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Goods record);

    Goods selectByPrimaryKey(Long id);

    List<Goods> selectAll();

    List<Goods> selectByTitileAndCategory(String goodTitle, String[] categorys);

    int updateByPrimaryKey(Goods record);
}