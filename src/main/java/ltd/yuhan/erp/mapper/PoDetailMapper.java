package ltd.yuhan.erp.mapper;

import java.util.List;
import ltd.yuhan.erp.model.PoDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PoDetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PoDetail record);

    PoDetail selectByPrimaryKey(Integer id);

    List<PoDetail> selectAll();

    int updateByPrimaryKey(PoDetail record);

    List<PoDetail> selectPoDetailByGoodsId(Integer goodsId);

}