package ltd.yuhan.erp;

import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.ocean.rawsdk.common.SDKResult;
import com.alibaba.trade.param.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class ShoppingOrderTest {

    @Test
    public void getOrder() throws JsonProcessingException {

        //设置appkey和密钥(seckey)
        ApiExecutor apiExecutor = new ApiExecutor("4855454","VI90KjSOEZ");

        AlibabaTradeGetSellerOrderListParam orderListParam= new AlibabaTradeGetSellerOrderListParam();
        SDKResult<AlibabaTradeGetSellerOrderListResult> execute = apiExecutor.execute(orderListParam,"894eab17-a695-4607-a549-10118a3152ec");
        ObjectMapper objectMapper = new ObjectMapper();
//        System.out.println(objectMapper.writeValueAsString(execute));
        AlibabaTradeGetSellerOrderListResult result = execute.getResult();
        AlibabaOpenplatformTradeModelTradeInfo[] result1 = result.getResult();
        for (AlibabaOpenplatformTradeModelTradeInfo info: result1
             ) {
            System.out.println(info.getBaseInfo().getCreateTime());
            AlibabaOpenplatformTradeModelProductItemInfo[] productItems = info.getProductItems();
            for (AlibabaOpenplatformTradeModelProductItemInfo p: productItems
                 ) {

                System.out.println(p.getProductID());
                System.out.println(p.getPrice());
                System.out.println(p.getName());
                System.out.println(p.getSubItemIDString().toString());
                System.out.println(p.getSkuInfos());
                System.out.println("****");

            }
            System.out.println("----");
        }


    }
}
