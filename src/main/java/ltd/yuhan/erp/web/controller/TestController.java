package ltd.yuhan.erp.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class TestController {

    @GetMapping("/goods")
    public String index(){
        return "test"; //当浏览器输入/index时，会返回 /templates/home.html页面
    }
}
