package com.baidu.ai.aip;

import com.baidu.ai.aip.auth.AuthService;
import com.baidu.ai.aip.utils.Base64Util;
import com.baidu.ai.aip.utils.FileUtil;
import com.baidu.ai.aip.utils.HttpUtil;

import java.net.URLEncoder;

/**
 * Created by Arcry on 2017/12/31.
 * 人脸更新--用于对人脸库中指定用户，更新其下的人脸图像。
 * 针对一个uid执行更新操作，新上传的人脸图像将覆盖此uid原有所有图像。
 */

public class FaceUpdate {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */

    public static String update() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/update";
        try {
            // 本地文件路径
            String filePath = "/Users/Arcry/Desktop/imgs/face/find/test_group_102/user_117.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

//            String filePath2 = "[本地文件路径]";
//            byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
//            String imgStr2 = Base64Util.encode(imgData2);
//            String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");

            //请求参数
            String uid = "test_user_117";
            //String groupId = "test_group_102";
            //String actionType = "replace";

            //String param = "uid=" + uid + "&images=" + imgParam +"&group_id="+ groupId+"&action_type"+actionType;
            String param = "uid=" + uid + "&images=" + imgParam ;
            //String param = "uid=" + "test_user_5" + "&images=" + imgParam + "," + imgParam2;

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
        FaceUpdate.update();
    }
}