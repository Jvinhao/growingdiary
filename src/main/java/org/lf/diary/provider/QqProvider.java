package org.lf.diary.provider;

import com.alibaba.fastjson.JSON;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.lf.diary.core.AccessTokenDTO;
import org.lf.diary.model.QqModel;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: Jvinh
 * @DateTime: 2020/3/4 14:15
 * @Description: TODO
 */
@Component
public class QqProvider {

    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code&client_id=" + accessTokenDTO.getClientId()
                + "&client_secret=" + accessTokenDTO.getClientSecret() + "&code=" + accessTokenDTO.getCode() + "&redirect_uri=" + accessTokenDTO.getRedirectUri();
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            String token = string.split("&")[0].split("=")[1];
            return token;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getOpenId(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://graph.qq.com/oauth2.0/me?access_token=" + accessToken;
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            String str = response.body().string();
            return str.substring(str.lastIndexOf(":") + 2, str.lastIndexOf('"'));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public QqModel getUserInfo(AccessTokenDTO accessTokenDTO) {
        OkHttpClient client = new OkHttpClient();
        String url = "https://graph.qq.com/user/get_user_info?access_token=" + accessTokenDTO.getAccessToken() +
                "&oauth_consumer_key=" + accessTokenDTO.getClientId() + "&openid=" + accessTokenDTO.getOpenId();
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            return JSON.toJavaObject(JSON.parseObject(string), QqModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
