package ltd.yuhan.erp.cron;

import ltd.yuhan.erp.mapper.ShoppingOrderDetailMapper;
import ltd.yuhan.erp.mapper.ShoppingOrderMapper;
import ltd.yuhan.erp.mapper.WarehouseOutMapper;
import ltd.yuhan.erp.mapper.WarehouseoutinfoDao;
import ltd.yuhan.erp.model.ShoppingOrder;
import ltd.yuhan.erp.model.WarehouseOut;
import ltd.yuhan.erp.model.Warehouseoutinfo;
import ltd.yuhan.erp.service.GoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Service
@EnableScheduling
//根据未完成订单和库存生成发货单，准备定时运行
public class WarehouseOutGet {

    @Autowired
    private ShoppingOrderMapper shoppingOrderMapper;
    @Autowired
    private ShoppingOrderDetailMapper shoppingOrderDetailMapper;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private WarehouseOutMapper warehouseOutMapper;
    @Autowired
    private WarehouseoutinfoDao warehouseoutinfoDao;
    private static final Logger logger = LoggerFactory.getLogger(WarehouseOutGet.class);



    //如果order处于未完结状态
    //找到所有orderdetail记录，
    //指定时间间隔5分钟
    @Scheduled(fixedRate=1000*60*5)
    public void getWarehouseOut(){

        //获取未完成order列表，0代表未完成，1代表完成
        List<ShoppingOrder> shoppingOrderByTerm = shoppingOrderMapper.getShoppingOrderByTerm("0");
        for (ShoppingOrder s:shoppingOrderByTerm
             ) {
            //获取shopping id
            Long soId = s.getId();
            Warehouseoutinfo warehouseoutinfo = new Warehouseoutinfo();
            //拿到最大的infoid的现有的最大值，新建的值在最大的基础上加1
            int infoId = warehouseoutinfoDao.selectMaxPrimaryKey() + 1;
            warehouseoutinfo.setOutinfoid(infoId);
            warehouseoutinfo.setOrderid(soId);
            warehouseoutinfo.setCreatetime(new Date());
            warehouseoutinfo.setStatus(0);

            List<Map> maps = shoppingOrderDetailMapper.selectSoDetailBySoId(soId);
            //循环订单中的每种商品，根据未发货量和库存量，计算出发货单应发货量
            for (int i = 0; i <maps.size() ; i++) {
                Map map = maps.get(i);
                //获取goodsid
                Long goodsId = (Long)map.get("goodsId");
                //根据goodsId取得现有库存
                int goodsQty = goodsService.getGoodsQty(goodsId);
                //取得该订单销售量
                int saleQty = (int)map.get("qty");

                //计算该订单该商品已发货量
                int goodsOutTotal = 0;
                List<WarehouseOut> warehouseOutByTerm = warehouseOutMapper.getWarehouseOutByTerm(goodsId, soId);
                for (WarehouseOut out: warehouseOutByTerm
                     ) {
                    goodsOutTotal += out.getQty();
                }
                //库存量和该订单的未发量的两者较小的，为应发货量
                int outGoods = Math.min(goodsQty, (saleQty - goodsOutTotal));
                if(outGoods > 0){
                    //一个订单进而有多个商品，可能之前已经创建过了这条info里，只有没有创建过，才再创建一次
                    if(warehouseoutinfoDao.selectMaxPrimaryKey() < infoId){
                        warehouseoutinfoDao.insert(warehouseoutinfo);
                    }

                    WarehouseOut warehouseOut = new WarehouseOut();
                    warehouseOut.setGoodsid(goodsId);
                    TimeZone.setDefault(TimeZone.getTimeZone("GMT+8:00"));
                    warehouseOut.setCreatetime(new Date());
                    warehouseOut.setOrderid(soId);
                    warehouseOut.setQty(outGoods);
                    //出库单状态设为0，代表生成出库单，但未发货状态
                    warehouseOut.setStatus(0);
                    warehouseOut.setInfoid(infoId);
                    warehouseOutMapper.insert(warehouseOut);
                }
            }

        }
        logger.info("根据库存变更生成发货单成功");
    }

}
