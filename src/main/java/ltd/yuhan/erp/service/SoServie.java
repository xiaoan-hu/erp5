package ltd.yuhan.erp.service;

import ltd.yuhan.erp.mapper.ShoppingOrderDetailMapper;
import ltd.yuhan.erp.mapper.ShoppingOrderMapper;
import ltd.yuhan.erp.mapper.WarehouseOutMapper;
import ltd.yuhan.erp.model.ShoppingOrder;
import ltd.yuhan.erp.model.WarehouseOut;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


public class SoServie {

    @Autowired
    private ShoppingOrderDetailMapper shoppingOrderDetailMapper;
    @Autowired
    private WarehouseOutMapper warehouseOutMapper;
    @Autowired
    private ShoppingOrderMapper shoppingOrderMapper;

    //传入Soid，判断所有的项目是否已全部发货，如果全部发货，将shopping order表中的status由0变为1

    public void setClose(Long soId){
        List<Map> maps = shoppingOrderDetailMapper.selectSoDetailBySoId(soId);
        //订单里所有商品未发货量和
        int soNotOutTotal = 0;
        for (Map map:maps
        ) {
            Long goodsId = (Long) map.get("goodsId");
            //这种商品出售的总数
            Integer qty = (Integer) map.get("qty");
            //计算该goodid和soid对应的发货单的发货数量
            int goodsOutTotal = 0;
            List<WarehouseOut> warehouseOutByTerm = warehouseOutMapper.getWarehouseOutByTerm(goodsId, Long.parseLong(soId));
            for (WarehouseOut out: warehouseOutByTerm
            ) {
                goodsOutTotal += out.getQty();
            }

            //加总所有商品未发出量
            soNotOutTotal += (qty- goodsOutTotal);
        }
        //soNotOutTotal如果为0，表示全部发货，将shopping order表中的status由0变为1
        if (soNotOutTotal == 0){
            ShoppingOrder shoppingOrder = shoppingOrderMapper.selectByPrimaryKey(soId);
            shoppingOrder.setStatus(1);
            shoppingOrderMapper.updateByPrimaryKey(shoppingOrder);
        }
    }
}
