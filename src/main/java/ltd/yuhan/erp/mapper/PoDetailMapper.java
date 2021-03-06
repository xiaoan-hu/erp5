package ltd.yuhan.erp.mapper;

import java.util.List;
import java.util.Map;

import ltd.yuhan.erp.model.PoDetail;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface PoDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PoDetail record);

    PoDetail selectByPrimaryKey(Integer id);

    List<PoDetail> selectAll();

    int updateByPrimaryKey(PoDetail record);

    List<PoDetail> selectPoDetailByGoodsId(long goodsId);

    List<Map> selectPoDetailByPoId(Integer poId);
}