package ltd.yuhan.erp.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import ltd.yuhan.erp.mapper.WarehouseOutMapper;
import ltd.yuhan.erp.mapper.WarehouseoutinfoDao;
import ltd.yuhan.erp.model.ShoppingOrder;
import ltd.yuhan.erp.model.WarehouseOut;
import ltd.yuhan.erp.model.Warehouseoutinfo;
import ltd.yuhan.erp.model.vo.GoodsInfoVo;
import ltd.yuhan.erp.model.vo.PoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping(value = "/mvc")
public class SoDetailController {

    @Autowired
    private WarehouseoutinfoDao warehouseoutinfoDao;
    @Autowired
    private WarehouseOutMapper warehouseOutMapper;

    @RequestMapping("/getWarehouseinfoPage")
    public String getOutListpage(){
        return "warehouseoutlist";
    }


    @RequestMapping(value = "/outinfolists", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Map> getOutInfoList() {
        List<Map> warehouseoutinfo = warehouseoutinfoDao.getWarehouseoutinfoByTerm(0);
        return warehouseoutinfo;

    }

    @RequestMapping(value = "/outdetail", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Map> getWarehouseOut(String infoId) {
        List<Map> warehouseOut = warehouseOutMapper.getWarehouseOutByInfoid(Long.parseLong(infoId));
        return warehouseOut;

    }

    @RequestMapping(value = "/saveWo", method = RequestMethod.POST)
    public void saveWo(String outs) {
        JSONArray array = JSON.parseArray(outs);
        WarehouseOut warehouseOut = new WarehouseOut();
        //更新warehouseout表中的数量和status
        for (int i = 0; i < array.size(); i++){
            HashMap <String,String> map = JSONObject.parseObject(array.getString(i).toString(),new TypeReference<HashMap<String,String>>() {});
            warehouseOut.setInfoid(Integer.parseInt(map.get("infoid")));
            warehouseOut.setStatus(1);
            warehouseOut.setQty(Integer.parseInt(map.get("qty")));
            warehouseOut.setOrderid(Long.parseLong(map.get("orderId")));
            warehouseOut.setCreatetime(new Date());
            warehouseOut.setGoodsid(Long.parseLong(map.get("goodsId")));
            warehouseOut.setId(Integer.parseInt(map.get("id")));
            warehouseOutMapper.updateByPrimaryKey(warehouseOut);
            //更新info表的status为1
            Warehouseoutinfo outinfo = warehouseoutinfoDao.selectByPrimaryKey(Integer.parseInt(map.get("infoid")));
            outinfo.setStatus(1);
            warehouseoutinfoDao.updateByPrimaryKey(outinfo);

        }

    }
}
