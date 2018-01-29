package com.doudou.facerecongnition;

import android.app.Application;
import android.content.Context;
import android.test.mock.MockContext;
import com.alibaba.fastjson.JSONObject;
import com.doudou.facerecongnition.entity.User;
import com.doudou.facerecongnition.util.MyHttpUtil;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.LinkedHashMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testInternet(){
        JSONObject json = MyHttpUtil.get("http://101.132.147.167:8080/ljj/user/hello");
        User user = json.getObject("data", User.class);
        System.out.println(user.getId() + "=====" + user.getImage_url());

    }

    @Test
    public void testPost(){
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("userAccount", "user555");
        params.put("password", "123456");
        JSONObject json = MyHttpUtil.post("http://101.132.147.167:8080/ljj/back/systemManager/loginVerify",
                params);
        System.out.println(json);
    }

    @Test
    public void testFaceRegister(){
        Context context = new MockContext();
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=5a6d4848");

    }
}