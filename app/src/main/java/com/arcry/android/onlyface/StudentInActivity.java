package com.arcry.android.onlyface;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;

import com.arcry.android.onlyface.bean.StudentBean;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 执行添加新学生Activity
 * StudentInActivity  ---->StudentIdImportActivity
 */
public class StudentInActivity extends AppCompatActivity {
    private  static  final  int TAKE_PHOTO = 1;
    private Uri imageUri;
    private String classId="";
    private File outputImage;
    private List<StudentBean> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentin_layout);
        SysApplication.getInstance().addActivity(this);

        MyApplication application = (MyApplication)getApplication();
        classId = application.getClassId();
        studentList = application.getStudentList();
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
        if (Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(StudentInActivity.this,
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
        if(requestCode  == TAKE_PHOTO){
            if(outputImage.exists()&&outputImage.length()==0){
                //如果文件为空，即拍照的时候点了取消按钮，返回ClassingActivity
                Intent intent_false = new Intent(StudentInActivity.this,ClassingActivity.class);
                startActivity(intent_false);
            }else{
                Intent studentIdImportIntent= new Intent(StudentInActivity.this,StudentIdImportActivity.class);
                studentIdImportIntent.putExtra("imageUri",imageUri.getPath());
                startActivity(studentIdImportIntent);
            }
        }
    }
}
