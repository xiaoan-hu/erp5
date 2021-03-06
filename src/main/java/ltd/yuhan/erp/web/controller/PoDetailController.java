package ltd.yuhan.erp.web.controller;

import ltd.yuhan.erp.model.GoodsCategory;
import ltd.yuhan.erp.model.PoDetail;
import ltd.yuhan.erp.service.PoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/detail")
public class PoDetailController {

    @Autowired
    private PoService poService;

    @GetMapping("/getCategory")
    @ResponseBody
    public List<GoodsCategory> getCategory(){
        return poService.getCategoty();
    }

    @RequestMapping("/getPoDetailPage")
    public String getPoDetailPage(){
        return "poDetail";
    }


    @RequestMapping(value = "/getPoDetail",method = RequestMethod.POST)
    @ResponseBody
    public List<Map> getPoDetail(String goodTitle,String category){
        String[] categorys = category.length()==0?new String[0]:category.split(",");
        return  poService.getPoDetail(goodTitle,categorys);
    }

    @RequestMapping(value = "/deletePo",method = RequestMethod.POST)
    @ResponseBody
    public void deletePo(String id){
//        String[] categorys = category.length()==0?new String[0]:category.split(",");
        System.out.println(id);
    }
}
