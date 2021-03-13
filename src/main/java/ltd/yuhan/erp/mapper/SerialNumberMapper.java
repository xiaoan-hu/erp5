package ltd.yuhan.erp.mapper;

import ltd.yuhan.erp.model.SerialNumber;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface SerialNumberMapper {
    SerialNumber getById(Integer id);

    int updateById(SerialNumber serialNumber);
}
