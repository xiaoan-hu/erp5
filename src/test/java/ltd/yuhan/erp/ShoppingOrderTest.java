package ltd.yuhan.erp;

import com.alibaba.ocean.rawsdk.ApiExecutor;
import com.alibaba.trade.param.AlibabaCreditOrderForDetail;
import com.alibaba.trade.param.AlibabaTradeGetSellerOrderListParam;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class ShoppingOrderTest {

    public void getOrder(){

        //设置appkey和密钥(seckey)
        ApiExecutor apiExecutor = new ApiExecutor("4855454","VI90KjSOEZ");

        AlibabaTradeGetSellerOrderListParam orderListParam= new AlibabaTradeGetSellerOrderListParam();
//        orderListParam.setCreateStartTime();

    }
}
