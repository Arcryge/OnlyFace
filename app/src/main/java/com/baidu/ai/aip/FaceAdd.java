package com.baidu.ai.aip;

import com.alibaba.fastjson.JSONObject;
import com.baidu.ai.aip.auth.AuthService;
import com.baidu.ai.aip.utils.Base64Util;
import com.baidu.ai.aip.utils.FileUtil;
import com.baidu.ai.aip.utils.HttpUtil;

import java.net.URLEncoder;

/**
 * Created by Arcry on 2017/12/30.
 * 人脸注册
 */

public class FaceAdd {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    //参数版
    public static String add(String filePath,String uid,String groupId) {
    // static String add() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";
        try {
            // 本地文件路径
            //String filePath = "/Users/Arcry/Desktop/imgs/face/find/test_group_102/test_user_117.png";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

//            String filePath2 = "[本地文件路径]";
//            byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
//            String imgStr2 = Base64Util.encode(imgData2);
//            String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

            //  请求参数
//            String uid = "test_user_117";
//            String userInfo = "userInfo127";
//            String groupId = "test_group_102";

            String param = "uid=" + uid + "&user_info=" + "" + "&group_id=" + groupId + "&images=" + imgParam+"&action_type=replace" ;
            //String param = "uid=" + uid + "&user_info=" + userInfo + "&group_id=" + groupId + "&images=" + imgParam + "," + imgParam2;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();

            String result = HttpUtil.post(url, accessToken, param);
            System.out.println("add   result:"+result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isAddSuccess(String filePath, String uid, String groupId){
        String result = FaceAdd.add(filePath,uid,groupId);
        int jsonLeng = JSONObject.parseObject(result).size();
        if(jsonLeng==1){
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        //FaceAdd.add();
    }
}