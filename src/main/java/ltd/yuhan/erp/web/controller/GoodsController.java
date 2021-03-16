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
    public List<Map> getGoodsList(String goodTitle,String category) {
        List<Goods> goodsAllList = goodsService.getGoodsByTitileAndCategory(goodTitle,category);
        return getViewMaps(goodsAllList);

    }

    @RequestMapping("/getGoodsPage")
    public String getGoodsPage() {
        return "goods";
    }




    // 查询库存，在返回list中添加库存数以及在途数
    private List<Map> getViewMaps(List<Goods> goodsAllList) {

        ArrayList<Map> returnList = new ArrayList<>();

        for (Goods goods: goodsAllList
             ) {
            Map returnMap= new HashMap<>();
            returnMap.put("picture","http://cbu01.alicdn.com/"+goods.getPicture());
            returnMap.put("title",goods.getTitle());
            returnMap.put("introduction",goods.getIntroduction());
            returnMap.put("price",goods.getPrice().toString());
            returnMap.put("longer", goods.getLonger().toPlainString());
            returnMap.put("wide",goods.getWide().toPlainString());
            returnMap.put("high",goods.getHigh().toPlainString());
            returnMap.put("weight",goods.getWeight().toPlainString());
            returnMap.put("quantity",goodsService.getGoodsQty(goods.getId()));
            returnMap.put("intrans",goodsService.getGoodsInTrans(goods.getId()));

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
