package learning.spring.zxh.demo.controller;

import learning.spring.zxh.demo.dto.QuestionDTO;
import learning.spring.zxh.demo.mapper.QuestionMapper;
import learning.spring.zxh.demo.mapper.UserMapper;
import learning.spring.zxh.demo.model.Question;
import learning.spring.zxh.demo.model.User;
import learning.spring.zxh.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null){//cookies不为空
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {//根据key找到自定义的cookie
                    String token = cookie.getValue();//获得cookie的value
                    User user = userMapper.findByToken(token);//根据cookie的value在数据库中获取相应user
                    if (user != null) {//如果存在用户信息且不为空
                        request.getSession().setAttribute("user",user);//将user推给request
                    }
                    break;
                }
            }
        }

        //model对象将后端储存的对象传回前端
        List<QuestionDTO> questionList = questionService.list();
        model.addAttribute("questions",questionList);
        return "index";
    }
}
