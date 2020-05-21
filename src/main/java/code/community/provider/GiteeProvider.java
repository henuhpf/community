package code.community.provider;

import code.community.dto.AccessTokenDTO;
import code.community.dto.GiteeUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;

@Component
public class GiteeProvider {
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType okHttpJSON = MediaType.get("application/json; charset=utf-8");
        String url = "https://gitee.com/oauth/token?grant_type=authorization_code&code=" + accessTokenDTO.getCode()
                + "&client_id=" + accessTokenDTO.getClient_id()
                + "&redirect_uri=" + accessTokenDTO.getRedirect_uri()
                + "&client_secret=" + accessTokenDTO.getClient_secret();
        OkHttpClient client = new OkHttpClient();
        JSONObject jsonObject = new JSONObject();
        RequestBody body = RequestBody.create(jsonObject.toJSONString(), okHttpJSON);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            HashMap hashMap = JSON.parseObject(string, HashMap.class);
            String access_token = (String) hashMap.get("access_token");
            return access_token;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public GiteeUser getUserInfo(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://gitee.com/api/v5/user?access_token=" + accessToken)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String string = response.body().string();
            GiteeUser giteeUser = JSON.parseObject(string, GiteeUser.class);
            return giteeUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
