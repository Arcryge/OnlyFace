package com.baidu.ai.aip;

import com.baidu.ai.aip.auth.AuthService;
import com.baidu.ai.aip.utils.Base64Util;
import com.baidu.ai.aip.utils.FileUtil;
import com.baidu.ai.aip.utils.HttpUtil;

import java.net.URLEncoder;

/**
 * Created by Arcry on 2017/12/30.
 * 人脸对比
 */

public class FaceMatch {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String match() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v2/match";
        try {
            // 本地文件路径
            String filePath = "/Users/Arcry/Desktop/imgs/face/match/match2.jpg";
            byte[] imgData = FileUtil.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String filePath2 = "/Users/Arcry/Desktop/imgs/face/find/test_group_102/user_117.jpg";
            byte[] imgData2 = FileUtil.readFileByBytes(filePath2);
            String imgStr2 = Base64Util.encode(imgData2);
            //String imgParam2 = URLEncoder.encode(imgStr2, "UTF-8");
            String imgParam2 = "%2F9j%2F4AAQSkZJRgABAQAAAQABAAD%2F2wBDAAEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH%2F2wBDAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQEBAQH%2FwAARCADIAJYDASIAAhEBAxEB%2F8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL%2F8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4%2BTl5ufo6erx8vP09fb3%2BPn6%2F8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL%2F8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3%2BPn6%2F9oADAMBAAIRAxEAPwD7WlnL8sTwP3bAHGTvOSmcZweO%2FLdTyarSE%2FextjI4wcsMODk84AAUg9gTjkg1lNNNMD5hI4JycKxOXU4XdgfLjrjnJwScl29gjPuBIyuPm%2FhBDE8YAHBOB3XgLuz%2BmQ96MXLmbtdNwcHe73i3dPqvK3mfkU4SrTnzTlKc3FydRWk7PdJLZ3Sk9raXbUmaYnj8vYTkAEHB5DZOeAPTaep5JzjoXLMHAToEIOcnpuPOecnOD6deThiMpNuCwyOPmIB6jP3QMZ5OcEnsM5Qlmmd1V2QknHAZsEMSwA55AypwD0LEZ%2FiquVNp7uO2%2FV%2Bv9d2S%2FaRi3zQklZLlvd2kknqmndxje3mls5PZNzklVPACggEZ6yAnjPXCkc5XHUEkl5ux2U5wMDOQfvAHGcknA9SBksxLA1yGpa%2Fpmh2d3qutapp%2BmWFpC015fX91BaWkEShmdpp55EjiRQAS0jKMHcWOWz8P%2FFP%2FAIKE%2FCLwU1zp3gyO6%2BI2sQwv5D6ezaZ4cSZGkjbzNbuYZJLqMsuf%2BJdYXUMgwq3SlkkpTjTTvVduTqpbOTe%2BvW1rPXfqtdKUcRNxcIOTfXlfLo5NbWd1rprvu0mz9F4rhOZGIQhTyWAztDE7ssNo4BJbgc5O0Fz4V8Wv2rfgf8G4LiLxR4rt9Q1y2JJ8MeHXi1bXjIm4eVPbQzC209%2Buf7Su7VO%2B4rlh%2BGPxS%2FbT%2BO%2FxYF1Yy%2BI28I6FLmM%2BH%2FChn023khAePy73VDK2q37PG3zxyXa2jlgv2ONc18sNeM5laUvJKXZ5gzZZpMuzSZY5LOecnucnIBNczxLc3CK7Wb7Xtt3e68t7m9PAVJ1H7ZNRfLZxTSau773fSzV92tdGfvt8Ef289O%2BMXxXk8IS%2BEU8MaBeQx2ujXk%2BpJfajdX85uBZjUAsUVvZC48kxrBA1xsldQLuRTuP6Gx%2FfJOeeRjGQDvPIOTkcc%2BpOCSMn%2BZH9lCWR%2FjF4SKED%2FirPBgJGRlf7bG6NxkkjIQZzlcn5gwJr%2BmRZ2bBBBU8NgHGMuCQByMnH0yCcnfnOhVm3VjKLfPy2drPaafdPRJpf4dW5GdXDxp152UuSLhZPe%2FvX1%2F7djL0TWvvM1VcdDgFGJOBk7ScDncQCdpHTPJHJXJkV1IxgqCoUO3Uk5ycAkDlR3wFKjrWcCzDBGAAFLcc4Yj%2B8fRcAH1xnktKrsV%2BYHIPHBH3SRng4%2BYYbPBPQnOTWkaN6b5k7pe6tUk227vr9lvW%2B6Vk02bQXI24vf%2FJpdXuua%2Fr0abJCg3DkHawU8cdx6c9M9yOB35rXIUE4Y427cYAXO5%2BSTyecDB7EkEncDY3AsoAG0EZ6dSSBxnPPT3GMHpUU43D7gzkhcZAOSwI4I4%2BQYB9SM8muacXClFyTv06L4p26vW%2Bu7srqzabNqUHPm1svdT7tXk%2FO3wp%2BttXe6510wXYMOjeoPfJAJ9xkdMkck4FcvdKY3Iw7KwUEAkYUb8ndjgNjBViegAGSSeouVdmYJtXBGCRnpuBySTjrgY9WAPPOJdKSrN95h0xntvA6E4IHzDvkEA5JY4Vv3kFzu7kveSstOZcu2q8%2FVa6NvqjFUVeN25X0bbTVmnZX621vs5JX5Y3OIvYmjt9vXOVyAeMliOpJ9TyT3xkkAcjcO0cqOpAJ27jjg46jJPGAVGNvdsZODXZX5Zw67WXAJOQRnlhg88cnjODyTnFcFfGSMPgAkuWCsCON0hA3bskY44IH3uoU5xjZJxX2eVPfq5NdXvo3rokruzu5lCVShZxaUnGV3t7spWu%2BZu2muut463VjaS8SIZBVgVUck5H3iv3cDOCOd2TgcH71FcSbq5kUhWwwIzgbgMCRSOW4zgY5zgEc5BortoUKs6akvZ283K%2FxS7dN7dbXT1aa5ufEO7jOml05uVPez0ck%2FwCt2tT6SSISoQGJ5wCCOfvFtpO0nhenADYwepMLkCJ9h2BSMsxwV5IIAyD8w6HBJYlhkjJ%2FIDx5%2B25qVp59hoOuzPK0Clm0y9W6EcvmyjIu1dod67d%2BPnYFiVIAGfl%2FxB%2B178eNVFzFa%2BPta06C4JjQ2t0ftKRSGQI32nYkisAUx5TDY4MiOJBk9NHEc6b5HGEVHRq0r3lZ26bN7919qTek8FZScZRfM1y6OD3l9lvTRa6uz5dU20%2F3I%2BIHxn%2BGnwutGu%2FG%2FjHR9GCxGSGxkuUl1e6CbiRZ6TbGW%2Fu%2F9WQTDbugbbvkUHdX5z%2FFP%2FgpI05v9L%2BEXhY264aOLxN4nQNMUG9TNZ6Hbz7EdQBJG99dzBi%2F76wVVbd%2BXur6vqWs3t1f6rqV3qWo3LCW5vb26ubm7upR5haS4uLmSSWVyzbnd5CzO5ycGucllCkZVVc4BAGCQrHDLg46LyMHoCcfep1K7l7tCUW9ne993st9Ene3l1iOjgI03y1GpKHLZczs7yldtPV62ttraybR6z8QPjF8RvifdyXfjvxZqWvbnLRW1zM0OnWzNI7M9ppNsIdPs3Gdm61tY5GUjezjJryhpkVnADNkZK8n%2Fnrn5mOfvHOc%2BwO0k1G04KklyQCV2543bSeuDx8oIHQDK8ksa8z8UePINJJhsNkt38rNJIHS3VVMifK6sN8jMN3yZRSCpbcCK4akanJLmnzSck5atpW5lG%2Bu75fOyasnqz1KNGUJRjSgpbcyUVpdvl0vZJrVrXo272Z6HNeW9hbma5uEgTcAzMwG9ju4UBvmJC8gYG0kkhWJrlpvE9pcF4rSGaYnjeSoR0BKkqA%2B8bSRx3GOSCprwe78TXmozuZJHn3kOd7MsYLM%2BVQFmVc8EPwcFcDA51NK8RT2jxs1spAP31Ytuyz4BBIGAu3bwBgtuJap9pUT5rK9tG07NapdLdu%2Byvdq56tDAqo5OpJactuXpfma3j0a0176tu599fsr%2BNNP0P4s%2BD59Wk%2BxWzeJ%2FCp%2B0PGxCxwanHNJIwzkIAi5YYAZmLMCpcf1B%2BHdZtNbtkuLOdJ45VjlR4zuSSJ8lJkKsflYYGMZBwDh9xr%2BOXw38QtLs7uyupolSZX";

            String param = "images=" + imgParam + "," + imgParam2;

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
}
//    public static void main(String[] args) {
//        FaceMatch.match(String filePath);
//    }
//}