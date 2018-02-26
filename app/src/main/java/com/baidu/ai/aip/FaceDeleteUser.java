package com.baidu.ai.aip;

import com.baidu.ai.aip.auth.AuthService;
import com.baidu.ai.aip.utils.HttpUtil;

/**
 * Created by Arcry on 2017/12/31.
 * 组内删除用户--用于将用户从某个组中删除，但不会删除用户在其它组的信息。
 * 当用户仅属于单个分组时，本接口将返回错误，请使用人脸删除接口
 */

public class FaceDeleteUser {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String deleteUser() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/deleteuser";
        try {
            //请求参数
            String uid = "201507117";//用户id
            String groupId = "102";//用户组id，多个的话用逗号分隔

            String param = "uid=" + uid + "&group_id=" + groupId;

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

    public static void main(String[] args) {
        FaceDeleteUser.deleteUser();
    }
}
