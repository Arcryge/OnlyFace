package com.baidu.ai.aip;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.ai.aip.auth.AuthService;
import com.baidu.ai.aip.utils.Base64Util;
import com.baidu.ai.aip.utils.FileUtil;
import com.baidu.ai.aip.utils.HttpUtil;

import java.math.BigDecimal;
import java.net.URLEncoder;

/**
 * Created by Arcry on 2017/12/30.
 * 人脸查找——识别 （已用户照片，以图搜人（在人脸库中））
 * 用于计算指定组内用户，与上传图像中人脸的相似度。
 */

public class Identify {
    public static String getUid() {
        return uid;
    }

    private static String uid="";

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String identify(String filePath,String classId) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/identify";
        try {
            // 本地文件路径
            //String filePath = "/Users/Arcry/Desktop/imgs/face/match/g0.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

//            String filePath2 = "[本地文件路径]";
//            byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
//            String imgStr2 = Base64Util.encode(imgData2);
//            String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

            //  请求参数
            //String groupId = "test_group_102";
            String userTopNum = "1";
            String faceTopNum = "1";

            String param = "group_id=" + classId + "&user_top_num=" + userTopNum + "&face_top_num=" + faceTopNum + "&images=" + imgParam ;
            //String param = "group_id=" + groupId + "&user_top_num=" + "1" + "&face_top_num=" + "1" + "&images=" + imgParam + "," + imgParam2;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();


            String result = HttpUtil.post(url, accessToken, param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isIdentifySuccess(String filePath, String classId){
        String result = Identify.identify(filePath,classId);
        JSONArray inresult = (JSONArray) JSONObject.parseObject(result).get("result");
        String nn = inresult.get(0).toString();
        uid = (String) JSONObject.parseObject(nn).get("uid");
        JSONArray scores = (JSONArray) JSONObject.parseObject(nn).get("scores");
        BigDecimal data = (BigDecimal)scores.get(0);
        Double s = data.doubleValue();
        if (s >= 80.0){
            return true;
        }
        return false;
    }

    public static String m_nIdentify(String filePath,String classId){
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/multi-identify";
        try {
            // 本地文件路径
            //String filePath = "/Users/Arcry/Desktop/imgs/face/match/g0.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            //  请求参数
            //String groupId = "test_group_102";
            String detect_top_num = "10";
            String user_top_num = "1";

            String param = "group_id=" + classId + "&detect_top_num=" + detect_top_num + "&user_top_num=" + user_top_num + "&images=" + imgParam ;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();
            String result = HttpUtil.post(url, accessToken, param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        //boolean a = Identify.isIdentifySuccess("/Users/Arcry/Desktop/imgs/face/match/g0.jpg","test_group_102");
        //System.out.println("a:--"+a);
    }
}