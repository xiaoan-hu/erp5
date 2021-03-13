package ltd.yuhan.erp.web.controller;



import ltd.yuhan.erp.model.Goods;
import ltd.yuhan.erp.model.vo.GoodsInfoVo;
import ltd.yuhan.erp.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/mvc")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/goods", produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<Map<String,String>> getGoodsList(String goodTitle,String category) {
        List<Goods> goodsAllList = goodsService.getGoodsByTitileAndCategory(goodTitle,category);
        return getViewMaps(goodsAllList);

    }




    // 查询库存，在返回list中添加库存数以及在途数
    private List<Map<String, String>> getViewMaps(List<Goods> goodsAllList) {

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


    @GetMapping(value = "/goodInfoVo")
    @ResponseBody
    public Map<String,Map<String, GoodsInfoVo[]>> getGoodsInfoVo() {
        return goodsService.getGoodsInfoVo();
    }

    @RequestMapping(value = "/refreshGoods", method = RequestMethod.POST)
    @ResponseBody
    public List<Goods> getRefreshGoodsGoodsInfoVo(String category, String title) {
        return goodsService.getGoodsByTitileAndCategory(title,category);
    }
}
