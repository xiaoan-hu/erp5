package ltd.yuhan.erp.cron;

import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.ocean.rawsdk.common.SDKResult;
import com.alibaba.trade.param.AlibabaOpenplatformTradeModelProductItemInfo;
import com.alibaba.trade.param.AlibabaOpenplatformTradeModelTradeInfo;
import com.alibaba.trade.param.AlibabaTradeGetSellerOrderListParam;
import com.alibaba.trade.param.AlibabaTradeGetSellerOrderListResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ltd.yuhan.erp.mapper.ShoppingOrderDetailMapper;
import ltd.yuhan.erp.mapper.ShoppingOrderMapper;
import ltd.yuhan.erp.model.ShoppingOrder;
import ltd.yuhan.erp.model.ShoppingOrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
@EnableScheduling
public class ShoppingOrderGet {
    @Autowired
    private ShoppingOrderMapper shoppingOrderMapper;
    @Autowired
    private ShoppingOrderDetailMapper shoppingOrderDetailMapper;
    private static final Logger logger = LoggerFactory.getLogger(ShoppingOrderGet.class);

    //指定时间间隔5分钟
    @Scheduled(fixedRate=1000*60*5)
    public void getOrder() throws JsonProcessingException {



        //设置appkey和密钥(seckey)
        ApiExecutor apiExecutor = new ApiExecutor("4855454","VI90KjSOEZ");

        AlibabaTradeGetSellerOrderListParam orderListParam= new AlibabaTradeGetSellerOrderListParam();
        // TODO 添加参数每五分钟抓取一次等待卖家发货的订单列表
        orderListParam.setOrderStatus("waitsellersend");
        //现在时间往前推五分钟，1分钟等于6万毫秒
        orderListParam.setCreateStartTime(new Date(System.currentTimeMillis() -60000*1440));
        SDKResult<AlibabaTradeGetSellerOrderListResult> execute = apiExecutor.execute(orderListParam,"894eab17-a695-4607-a549-10118a3152ec");
        ObjectMapper objectMapper = new ObjectMapper();
        AlibabaTradeGetSellerOrderListResult result = execute.getResult();
        AlibabaOpenplatformTradeModelTradeInfo[] result1 = result.getResult();
        logger.info("导入阿里销售量数量"+result1.length);
        for (AlibabaOpenplatformTradeModelTradeInfo info: result1
             ) {

            //如果查不到订单记录再插入，已支付订单暂不考虑更新的情况
            if (shoppingOrderMapper.selectByPrimaryKey(info.getBaseInfo().getId()) == null){
                ShoppingOrder shoppingOrder = new ShoppingOrder();
                shoppingOrder.setCreatetime(info.getBaseInfo().getCreateTime());
                shoppingOrder.setShopper(info.getBaseInfo().getBuyerLoginId());
                shoppingOrder.setId(info.getBaseInfo().getId());
                shoppingOrder.setTotalprice(info.getBaseInfo().getTotalAmount());
                shoppingOrder.setStatus(0);
                shoppingOrderMapper.insert(shoppingOrder);

                AlibabaOpenplatformTradeModelProductItemInfo[] productItems = info.getProductItems();
                for (AlibabaOpenplatformTradeModelProductItemInfo p: productItems
                ) {
                    ShoppingOrderDetail shoppingOrderDetail = new ShoppingOrderDetail();
                    shoppingOrderDetail.setShoppingorderid(info.getBaseInfo().getId());
                    shoppingOrderDetail.setTotalprice(p.getItemAmount());
                    shoppingOrderDetail.setQty(p.getQuantity().intValue());
//                    shoppingOrderDetail.setGoodsid(p.getSkuID());
                    //如果一种商品没有skuid，只有productid,在订单信息里拿到的skuid有可能是null
                    if (p.getSkuID() != null){
                        shoppingOrderDetail.setGoodsid(p.getSkuID());
                    }else{
                        shoppingOrderDetail.setGoodsid(p.getProductID());
                    }
                    shoppingOrderDetailMapper.insert(shoppingOrderDetail);
                }

            }


        }


    }
}
