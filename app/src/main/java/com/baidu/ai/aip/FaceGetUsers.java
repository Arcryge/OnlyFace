package com.baidu.ai.aip;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.ai.aip.auth.AuthService;
import com.baidu.ai.aip.utils.HttpUtil;

/**
 * Created by Arcry on 2017/12/30.
 * 组内用户列表查询
 */

public class FaceGetUsers {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String getUsers(String groupId) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/getusers";
        try {
            //请求参数
            //String groupId = "test_group_102";
            String param = "group_id=" + groupId + "&start=" + 0 + "&end=" + 100;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getAuth();
            String result = HttpUtil.post(url, accessToken, param);
            System.out.println("getUsers  result:"+result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isExist_UserId(String userId,String classId){
        String result = FaceGetUsers.getUsers(classId);
        JSONArray students = (JSONArray) JSONObject.parseObject(result).get("result");
        Integer num = (Integer) JSONObject.parseObject(result).get("result_num");
        for (int i=0;i<num;i++){
            String n=  students.get(i).toString();
            String n1 = (String) JSONObject.parseObject(n).get("uid");
            if (n1.equals(userId)){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        FaceGetUsers.getUsers("104");
        //boolean a = isExist_UserId("","");
    }
}