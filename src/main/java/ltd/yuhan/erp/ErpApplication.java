package ltd.yuhan.erp;

import ltd.yuhan.erp.mapper.GoodsMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@Configuration
@SpringBootApplication
@RestController
//@MapperScan(
//        //指定扫描包
//        basePackages = "ltd.yuhan.erp.*"
//)
@ComponentScan
public class ErpApplication {

    public static void main(String[] args) {
        SpringApplication.run(ErpApplication.class, args);
    }

    @RequestMapping("/hello")
    public String hello(){
        return "Hello Spring";
    }

}
