package learning.spring.zxh.demo.controller;

import learning.spring.zxh.demo.mapper.QuestionMapper;
import learning.spring.zxh.demo.mapper.UserMapper;
import learning.spring.zxh.demo.model.Question;
import learning.spring.zxh.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {

    @Autowired(required = false)
    private QuestionMapper questionMapper;
    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/publish")
    public String doPublic(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model //通过创建model对象将数据回传到网页
    ) {
        model.addAttribute("title",title);
        model.addAttribute("content",content);
        model.addAttribute("tag",tag);
        //检测问题是否合法
        if (title == null || title == "") {
            model.addAttribute("error", "标题不能为空！");
            return "publish";
        }
        if (content == null || content == "") {
            model.addAttribute("error", "内容描述不能为空！");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute("error", "至少有一个标签！");
            return "publish";
        }
        //检测是否登录
        User user = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {//cookies不为空
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {//根据key找到自定义的cookie
                    String token = cookie.getValue();//获得cookie的value
                    user = userMapper.findByToken(token);//根据cookie的value在数据库中获取相应user
                    if (user != null) {//如果存在用户信息且不为空
                        request.getSession().setAttribute("user", user);//将user推给request
                    }
                    break;
                }
            }
            if (user == null) {
                model.addAttribute("error", "用户未登录");
                return "publish";
            }
        }
        if (cookies == null) {
            model.addAttribute("error", "用户未登录！");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        question.setTag(tag);
        question.setCreator(user.getId());


        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.creat(question);
        return "redirect:/";//成功创键，重定向到首页
    }
}
