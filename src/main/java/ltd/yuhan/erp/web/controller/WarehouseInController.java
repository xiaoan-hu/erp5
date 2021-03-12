package ltd.yuhan.erp.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ltd.yuhan.erp.model.WarehouseIn;
import ltd.yuhan.erp.service.WarehouseInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


@Controller
@RequestMapping(value = "/in")
public class WarehouseInController {
    @Autowired
    WarehouseInService inService;

    @RequestMapping(value = "/saveIn", method = RequestMethod.POST)
    @ResponseBody
    public boolean saveIn(String receiveGood) {
        inService.saveIn(receiveGood);
        return true;
    }

}
