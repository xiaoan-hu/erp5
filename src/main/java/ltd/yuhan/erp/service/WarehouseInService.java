package ltd.yuhan.erp.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ltd.yuhan.erp.mapper.PoMapper;
import ltd.yuhan.erp.mapper.WarehouseInMapper;
import ltd.yuhan.erp.model.Po;
import ltd.yuhan.erp.model.WarehouseIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
@Component
public class WarehouseInService {
    @Autowired
    private PoMapper poMapper;
    @Autowired
    private WarehouseInMapper inMapper;

    public void saveIn(String inJson){
        Date current = new Date(System.currentTimeMillis());
        JSONArray array = JSON.parseArray(inJson);
        int poId = JSONObject.parseObject(array.getString(0)).getInteger("poId");
        int status = JSONObject.parseObject(array.getString(1)).getInteger("status");
        JSONArray warehouseInJson = JSON.parseArray(array.getString(2));
        for (int i = 0; i < warehouseInJson.size(); i++) {
            JSONObject obj = JSONObject.parseObject(warehouseInJson.getString(i));
            if (obj.getInteger("overQty")==0) continue;
            WarehouseIn in = new WarehouseIn();
            in.setPoid(poId);
            in.setGoodsid(obj.getLong("goodsId"));
            in.setQty(obj.getInteger("overQty"));
            in.setCreatetime(current);
            inMapper.insert(in);
        }
        Po po = new Po();
        po.setId(poId);
        po.setStatus(status);
        poMapper.updateByPrimaryKey(po);
    }
}
