package learning.spring.zxh.demo.provider;


import com.alibaba.fastjson.JSON;
import learning.spring.zxh.demo.dto.AccessTokenDTO;
import learning.spring.zxh.demo.dto.GithubUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component  //把当前类初始化储存到Spring容器的上下文
public class GithubProvider {
    //使用accessToken的DTO向github发出请求，从响应中解析出用户token
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        //根据Github的指南使用post方法发出该请求
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder()
                .url("https://github.com/login/oauth/access_token")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {//接收
            String string = response.body().string();
            //parse the response to get token
            String token = string.split("&")[0].split("=")[1];
            //System.out.println(token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //使用accessToken向Github发出请求，返回包含用户信息的GithubUser对象
    public GithubUser getUser(String accessToken) {
        //根据Github的指南使用get方法发出该请求
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            //使用JSON.parseObject解析response的body部分，将数据存入GithubUser对象
            GithubUser githubUser = JSON.parseObject(string, GithubUser.class);
            return githubUser;
        } catch (IOException e) {
        }
        return null;
    }
}

