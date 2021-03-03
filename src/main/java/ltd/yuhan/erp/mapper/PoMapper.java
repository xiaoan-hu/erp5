package ltd.yuhan.erp.mapper;

import java.util.List;
import ltd.yuhan.erp.model.Po;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Po record);

    Po selectByPrimaryKey(Integer id);

    List<Po> selectAll();

    int updateByPrimaryKey(Po record);
}