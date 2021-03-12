package ltd.yuhan.erp.mapper;

import java.util.List;
import java.util.Map;

import ltd.yuhan.erp.model.Po;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface PoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Po record);

    Po selectByPrimaryKey(Integer id);

    List<Po> selectAll();

    int updateByPrimaryKey(Po record);

    Map<String,Integer> getId(Po record);

    List<Po> getPoByTerm(String status, String goodsTitle, String category, String orderId);
}