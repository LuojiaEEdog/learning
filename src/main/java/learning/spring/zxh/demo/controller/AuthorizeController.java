package learning.spring.zxh.demo.controller;


import learning.spring.zxh.demo.dto.AccessTokenDTO;
import learning.spring.zxh.demo.dto.GithubUser;
import learning.spring.zxh.demo.mapper.UserMapper;
import learning.spring.zxh.demo.model.User;
import learning.spring.zxh.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect}")
    private String redirectUri;

    @Autowired(required = false)
    private UserMapper userMapper;

    @GetMapping("/callback")
    //接收github跳转回的callback页面，获得返回的code与state
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           //HttpServletRequest request,
                           HttpServletResponse response
                           ){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser= githubProvider.getUser(accessToken);
        //System.out.println(githubUser.getName());//test
        if (githubUser!=null){
            User user = new User();
            String token = UUID.randomUUID().toString();//生成随机cookie
            user.setToken(token);
            user.setName(githubUser.getName());
            user.setAccountId(String.valueOf(githubUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setAvatarUrl(githubUser.getAvatarUrl());
            userMapper.insert(user);//将用户信息注入数据库
            response.addCookie(new Cookie("token",token));
            //login success
            //request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }
        else{
            //login failed
            return "redirect:/";
        }
    }
}
