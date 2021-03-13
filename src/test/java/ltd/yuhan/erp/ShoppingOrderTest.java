package ltd.yuhan.erp;

import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.ocean.rawsdk.common.SDKResult;
import com.alibaba.trade.param.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ltd.yuhan.erp.mapper.ShoppingOrderDetailMapper;
import ltd.yuhan.erp.mapper.ShoppingOrderMapper;
import ltd.yuhan.erp.model.ShoppingOrder;
import ltd.yuhan.erp.model.ShoppingOrderDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class ShoppingOrderTest {
    @Autowired
    private ShoppingOrderMapper shoppingOrderMapper;
    @Autowired
    private ShoppingOrderDetailMapper shoppingOrderDetailMapper;

    @Test
    public void getOrder() throws JsonProcessingException {



        //设置appkey和密钥(seckey)
        ApiExecutor apiExecutor = new ApiExecutor("4855454","VI90KjSOEZ");

        AlibabaTradeGetSellerOrderListParam orderListParam= new AlibabaTradeGetSellerOrderListParam();
        // TODO 添加参数每五分钟抓取一次等待卖家发货的订单列表
        orderListParam.setOrderStatus("waitsellersend");
        //现在时间往前推五分钟，1分钟等于6万毫秒
        orderListParam.setCreateStartTime(new Date(System.currentTimeMillis() -60000*60*24));
        SDKResult<AlibabaTradeGetSellerOrderListResult> execute = apiExecutor.execute(orderListParam,"894eab17-a695-4607-a549-10118a3152ec");
        ObjectMapper objectMapper = new ObjectMapper();
        AlibabaTradeGetSellerOrderListResult result = execute.getResult();
        AlibabaOpenplatformTradeModelTradeInfo[] result1 = result.getResult();
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

//                //输出订单创建时间
//                System.out.println("订单创建时间为："+info.getBaseInfo().getCreateTime());
//                //输出买家登录名
//                System.out.println("买家登录名："+info.getBaseInfo().getBuyerLoginId());
//                //输出订单编号
//                System.out.println("订单编号为："+info.getBaseInfo().getId());
//                //输出订单总价
//                System.out.println("订单总价为"+info.getBaseInfo().getTotalAmount());
//                System.out.println("---");
                //
                AlibabaOpenplatformTradeModelProductItemInfo[] productItems = info.getProductItems();
                for (AlibabaOpenplatformTradeModelProductItemInfo p: productItems
                ) {
                    ShoppingOrderDetail shoppingOrderDetail = new ShoppingOrderDetail();
                    shoppingOrderDetail.setShoppingorderid(info.getBaseInfo().getId());
                    shoppingOrderDetail.setTotalprice(p.getItemAmount());
                    shoppingOrderDetail.setQty(p.getQuantity().intValue());
                    shoppingOrderDetail.setGoodsid(p.getSkuID());

//                    //拿到这一项的总价
//                    System.out.println("单项总价"+p.getItemAmount());
//                    //拿到卖出的数量
//                    System.out.println("单项数量"+p.getQuantity());
//                    //拿到goodsid
//                    System.out.println("商品i在"+p.getSkuID());
//                    System.out.println("****");
//
                    shoppingOrderDetailMapper.insert(shoppingOrderDetail);
                }
//                System.out.println("----");
            }


        }


    }
}
