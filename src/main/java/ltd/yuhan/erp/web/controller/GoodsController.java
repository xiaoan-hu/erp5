package ltd.yuhan.erp.web.controller;



import com.sun.tools.javac.util.Convert;
import ltd.yuhan.erp.model.Goods;
import ltd.yuhan.erp.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/mvc")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping(value = "/goods", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Map<String,String>> getGoodsList() {
        List<Goods> goodsAllList = goodsService.getGoodsAllList();
        // TODO 查询库存，在返回list中添加库存数以及在途数
        List<Map<String,String>> returnList =new ArrayList<Map<String, String>>();

        for (Goods goods: goodsAllList
             ) {
            Map<String,String> returnMap= new HashMap<>();
            returnMap.put("title",goods.getTitle());
            returnMap.put("introduction",goods.getIntroduction());
            returnMap.put("price",goods.getPrice().toString());
            returnMap.put("longer", goods.getLonger().toPlainString());
            returnMap.put("wide",goods.getWide().toPlainString());
            returnMap.put("high",goods.getHigh().toPlainString());
            returnMap.put("weight",goods.getWeight().toPlainString());
            returnMap.put("quantity",String.valueOf(goodsService.getGoodsQty(goods.getId())));
            returnMap.put("intrans",String.valueOf(goodsService.getGoodsInTrans(goods.getId())));

            returnList.add(returnMap);

        }

        return returnList;

    }
}
