package com.arcry.android.onlyface;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.arcry.android.onlyface.bean.StudentBean;
import com.baidu.ai.aip.Identify;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FaceSignActivity extends AppCompatActivity {
    private  static  final  int TAKE_PHOTO = 1;
    private Uri imageUri;
    private String classId="";
    private File outputImage;
    private List<StudentBean> studentList;
    private boolean identify_Success=false;
    private String identifyUid="";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.facesign_layout);
        SysApplication.getInstance().addActivity(this);


        MyApplication application = (MyApplication)getApplication();
        classId=application.getClassId();
        //创建File对象，用于存储拍照后的图片
        outputImage = new File(getExternalCacheDir(),"output_image.jpg");
        try {
            if (outputImage.exists()){
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("imageUri000:"+imageUri);
        if (Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(FaceSignActivity.this,
                    "com.android.OnlyFace.fileprovider",outputImage);
        }else {
            imageUri = Uri.fromFile(outputImage);
        }
        //启动相机程序
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        progressDialog = new ProgressDialog(FaceSignActivity.this);
        progressDialog.setTitle("");
        progressDialog.setMessage("正在查找。。。");
        progressDialog.show();
        if(requestCode  == TAKE_PHOTO){
            if(outputImage.exists()&&outputImage.length()==0){
                Intent intent_false = new Intent(FaceSignActivity.this,ClassingActivity.class);
                startActivity(intent_false);
            }else{
                sendHttpRequest("Identify");
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if (identify_Success){
                            Toast.makeText(FaceSignActivity.this,identifyUid+"已签到，下一位",Toast.LENGTH_LONG).show();
                                sendHttpRequest("Minus");
                        }else {
                            Toast.makeText(FaceSignActivity.this,"您不在本课堂！",Toast.LENGTH_LONG).show();
                        }
                        Intent get_Intent = getIntent();
                        finish();
                        get_Intent.putExtra("imageUri",imageUri.getPath());
                        startActivity(get_Intent);
                    }
                }, 5000);//5秒后执行Runnable中的run方法
            }
        }
    }
    // whichMethod判断使用那种方法
    // 像联网进行数据请求这种耗时操作，最好都是放到子线程中进行，以避免阻塞主线程
    public void sendHttpRequest(final String whichMethod) {
        new Thread(new Runnable() {
            public void run() {
                switch (whichMethod){
                    case "Identify":
                        identify_Success = Identify.isIdentifySuccess(imageUri.getPath(),classId);
                        identifyUid = Identify.getUid();
                        break;
                    case "Minus":
                        MyApplication application = (MyApplication) getApplication();
                        studentList = application.getStudentList();
                        for (int i = 0;i<studentList.size();i++){
                            if (identifyUid.equals(studentList.get(i).getUid().toString())){
                                studentList.remove(i);
                            }
                        }
                        application.setStudentList(studentList);
                        break;
                }
            }
        }).start();
    }

}
