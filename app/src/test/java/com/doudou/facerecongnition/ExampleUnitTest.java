package com.doudou.facerecongnition;

import android.content.Context;
import android.test.mock.MockContext;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.doudou.facerecongnition.constant.GlobalVar;
import com.doudou.facerecongnition.entity.Course;
import com.doudou.facerecongnition.entity.Student;
import com.doudou.facerecongnition.util.Base64;
import com.doudou.facerecongnition.util.FileUtil;
import com.doudou.facerecongnition.util.MyHttpUtil;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import org.junit.Test;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;

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
        Student user = json.getObject("data", Student.class);
        System.out.println(user.getId() + "=====" + user.getImagePath());

    }

    @Test
    public void testPost(){
        LinkedHashMap<String, Object> para = new LinkedHashMap<>();
        para.put("teacherId", "15");

        JSONObject json = MyHttpUtil.post(GlobalVar.myServer + "/course/getCourseList",
                para);
        if (((JSONObject)json.get("data")).isEmpty()){

            System.out.println(json.get("null"));
        }
        //List<Course> courses = JSON.parseArray(((JSONObject)json.get("data")).get("courseList").toString(), Course.class);
        System.out.println(json);
        System.out.println("==================================================");
        //System.out.println(courses.size());
    }

    @Test
    public void testFaceRegister(){
        Context context = new MockContext();
        SpeechUtility.createUtility(context, SpeechConstant.APPID + "=5a6d4848");

    }

    @Test
    public void testBase64(){
        try {
            FileInputStream fin = new FileInputStream("E:/b.jpg");
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String str = Base64.encode(bytes);
            new FileOutputStream(new File("E:/a.jpg")).write(Base64.decode(str));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}