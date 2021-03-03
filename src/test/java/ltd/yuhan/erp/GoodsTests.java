package ltd.yuhan.erp;

import ltd.yuhan.erp.model.Goods;
import ltd.yuhan.erp.service.GoodsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GoodsTests {

    @Autowired
    private GoodsService goodsService;

    @Test
    public void getAllGoodsList(){

        List<Goods> goodsAllList = goodsService.getGoodsAllList();
        for ( Goods goods:goodsAllList
             ) {
            System.out.println(goods);
        }

        System.out.println(goodsAllList);
    }
    @Test
    public void getGoodQty(){
        int goodsQty = goodsService.getGoodsInTrans(1);
        System.out.println(goodsQty);

    }


}
