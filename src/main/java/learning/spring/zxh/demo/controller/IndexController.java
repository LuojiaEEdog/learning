package learning.spring.zxh.demo.controller;

import learning.spring.zxh.demo.mapper.UserMapper;
import learning.spring.zxh.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null){//cookies不为空
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {//根据key找到自定义的cookie
                    String token = cookie.getValue();//获得cookie的value
                    User user = userMapper.findByToken(token);//根据cookie的value获取相应user
                    if (user != null) {//如果存在
                        request.getSession().setAttribute("user",user);//将user推给request
                    }
                    break;
                }
            }
        }

        return "index";
    }
}
