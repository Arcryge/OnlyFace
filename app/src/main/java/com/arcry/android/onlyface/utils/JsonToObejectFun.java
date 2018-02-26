package com.arcry.android.onlyface.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.arcry.android.onlyface.bean.StudentBean;
import com.baidu.ai.aip.FaceGetUsers;

import java.util.List;

/**
 * Created by Arcry on 2018/1/9.
 */

public class JsonToObejectFun {
    public static List<StudentBean> jsonToObj(String jsonString){
        //解析对象
//        String jsonString = "[{\"id\":\"11\",\"name\":\"s1\"}," +
//                "{\"id\":\"16\",\"name\":\"s6\"}]";
        JSONArray students = (JSONArray) JSONObject.parseObject(jsonString).get("result");
        System.out.println("=============jsonString================");
        System.out.println(jsonString);
        //List<StudentBean> studentBeanList = JSON.parseArray(jsonString, StudentBean.class);
        List<StudentBean> studentBeanList = JSON.parseObject(String.valueOf(students),new TypeReference<List<StudentBean>>(){});
        System.out.println("==========List<StudentBean>=============");
        System.out.println(studentBeanList.toString());
//        for (int i=0;i<studentBeanList.size();i++){
//            if (studentBeanList.get(i).getUid().equals("201507117")){
//                studentBeanList.remove(i);
//            }
//            System.out.println(studentBeanList.get(i).getUid());
//        }
        return studentBeanList;
    }
    public static void main(String[] args) {
        String result = FaceGetUsers.getUsers("test_group_102");
        JsonToObejectFun.jsonToObj(result);
    }
}