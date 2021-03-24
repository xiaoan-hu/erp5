package ltd.yuhan.erp.web.controller;

import ltd.yuhan.erp.mapper.ShoppingOrderDetailMapper;
import ltd.yuhan.erp.mapper.ShoppingOrderMapper;
import ltd.yuhan.erp.mapper.WarehouseOutMapper;
import ltd.yuhan.erp.model.Goods;
import ltd.yuhan.erp.model.ShoppingOrder;
import ltd.yuhan.erp.model.ShoppingOrderDetail;
import ltd.yuhan.erp.model.WarehouseOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/mvc")
public class ShoppingOrderController {
    @Autowired
    private ShoppingOrderMapper shoppingOrderMapper;
    @Autowired
    private ShoppingOrderDetailMapper shoppingOrderDetailMapper;
    @Autowired
    private WarehouseOutMapper warehouseOutMapper;

    @RequestMapping(value = "/shoppingorders", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<ShoppingOrder> getOrdersList() {
        List<ShoppingOrder> shoppingOrderByTerm = shoppingOrderMapper.getShoppingOrderByTerm("0", "", "", "", "");
        return shoppingOrderByTerm;

    }

    @RequestMapping("/getSoPage")
    public String getSoPage() {
        return "shoppingOrder";
    }

//    @RequestMapping("/getSodetailPage")
//    public String getSodetailPage(String soid, Model model) {
//        model.addAttribute("soid", soid);
//        return "shoppingOrderDetail";
//    }


    @RequestMapping(value = "/sodetails", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Map> getSoDeatail(String soId){
        List<Map> maps = shoppingOrderDetailMapper.selectSoDetailBySoId(Long.parseLong(soId));
        for (Map map:maps
             ) {
            Long goodsId = (Long) map.get("goodsId");
            //计算该goodid和soid对应的发货单的发货数量
            int goodsOutTotal = 0;
            List<WarehouseOut> warehouseOutByTerm = warehouseOutMapper.getWarehouseOutByTerm(goodsId, Long.parseLong(soId));
            for (WarehouseOut out: warehouseOutByTerm
            ) {
                goodsOutTotal += out.getQty();
            }
            map.put("picture","http://cbu01.alicdn.com/"+map.get("picture"));
            map.put("goodsOutTotal",goodsOutTotal);
        }
        return maps;
    }
}
