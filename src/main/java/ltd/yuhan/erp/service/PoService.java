package ltd.yuhan.erp.service;

import ltd.yuhan.erp.mapper.*;
import ltd.yuhan.erp.model.GoodsCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Component
public class PoService {
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;
    @Autowired
    private PoDetailMapper poDetailMapper;

    public List<GoodsCategory> getCategoty(){
        return goodsCategoryMapper.selectAll();
    }

    public List<Map> getPoDetail(String goodTitle, String[] categorys){
        return poDetailMapper.getPoDetail(goodTitle,categorys);
    }

//    public void deletePo()
}
