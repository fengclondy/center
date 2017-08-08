package cn.htd.pricecenter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.htd.common.dao.util.RedisDB;

/**项目中加入数据库配置，测试是否配置成功。
 * Created by GZG on 2016/10/26.
 */
@Controller
@RequestMapping("/test")
public class TestController {


    @Autowired
    private RedisDB redisDB;

    @RequestMapping("/testJDBC")
    public String testJDBCMethod(Model model){
        return "/demo/user";
    }
}
