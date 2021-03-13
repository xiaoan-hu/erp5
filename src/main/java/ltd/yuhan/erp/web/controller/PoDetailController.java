package ltd.yuhan.erp.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ltd.yuhan.erp.model.GoodsCategory;
import ltd.yuhan.erp.model.vo.GoodsInfoVo;
import ltd.yuhan.erp.model.vo.PoVo;
import ltd.yuhan.erp.service.PoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/detail")
public class PoDetailController {

    @Autowired
    private PoService poService;

    @GetMapping("/getCategory")
    @ResponseBody
    public List<GoodsCategory> getCategory() {
        return poService.getCategoty();
    }

    @RequestMapping(value = "/savePo", method = RequestMethod.POST)
    @ResponseBody
    public PoVo savePo(String selectedGood) {
        JSONArray array = JSON.parseArray(selectedGood);
        List<GoodsInfoVo> selectedGoods = new ArrayList<>();
        for (int i = 0; i < array.size(); i++){
            GoodsInfoVo vo = JSONObject.parseObject(array.getString(i).toString(), GoodsInfoVo.class);
            selectedGoods.add(vo);
        }
        return poService.savePo(selectedGoods);
    }

    @RequestMapping("/getPoAndDetailPage")
    public String getPoAndDetailPage() {
        return "poAndDetail";
    }

    @RequestMapping("/getPoDetailPage")
    public String getPoDetailPage() {
        return "poDetail";
    }

    @RequestMapping("/getAddPoPage")
    public String getAddPoPage() {
        return "addPo";
    }


    @RequestMapping(value = "/getDetail", method = RequestMethod.POST)
    @ResponseBody
    public List<Map> getDetailVo(String id) {
        return poService.getPoDetailVo(id);
    }

    @RequestMapping(value = "/getPoAndDetail", method = RequestMethod.GET)
    @ResponseBody
    public List<PoVo> getPoAndDetail(String status, String title, String category, String orderId) {
        return poService.getPoAndDetail(status,title,category,orderId);
    }

}