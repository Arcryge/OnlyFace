package com.arcry.android.onlyface;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.arcry.android.onlyface.bean.StudentBean;
import com.arcry.android.onlyface.utils.JsonToObejectFun;
import com.baidu.ai.aip.FaceGetList;
import com.baidu.ai.aip.FaceGetUsers;

import java.util.List;
/**
 * 上课ing____Activity
 * 正在上课的班级-->添加新学生
 *              -->刷脸签到
 *              -->课堂情况
 * */
public class ClassingActivity extends AppCompatActivity {
    private boolean isExist=false;
    private String classId="";
    private TextView classIdTextView;
    private Button studentIn;
    private Button faceSign;
    private Button faceSignMulti;
    private Button classCase_btn;
    private List<StudentBean> studentList;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classing_layout);
        SysApplication.getInstance().addActivity(this);
        classIdTextView = (TextView) findViewById(R.id.classId);
        studentIn = (Button) findViewById(R.id.studentIn);
        faceSign = (Button) findViewById(R.id.faceSign);
        //faceSignMulti = (Button) findViewById(R.id.faceSign_multi);
        classCase_btn = (Button) findViewById(R.id.class_case);

        progressDialog = new ProgressDialog(ClassingActivity.this);
        progressDialog.setTitle("");
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        MyApplication application = (MyApplication)getApplication();
        classId = application.getClassId();
        studentList = application.getStudentList();
        sendHttpRequest("FaceGetList");
        System.out.println("studentList+++++++++++++++++++++++++++++++"+studentList);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isExist){
                    Intent intent = new Intent(ClassingActivity.this, MainActivity.class);
                    progressDialog.dismiss();
                    Toast.makeText(ClassingActivity.this,"创建班级失败！请新建或进入已有班级！",Toast.LENGTH_LONG).show();
                    startActivity(intent);
                }
                progressDialog.dismiss();
            }
        }, 5000);//3秒后执行Runnable中的run方法
        classIdTextView.setText("当前课程号："+classId);
        studentIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addStudentIntent = new Intent(ClassingActivity.this,StudentInActivity.class);
                startActivity(addStudentIntent);
            }
        });

        faceSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent faceSignIntent = new Intent(ClassingActivity.this,SignActivity.class);
                startActivity(faceSignIntent);
            }
        });

        classCase_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassingActivity.this,ClassCaseActivity.class);
                startActivity(intent);
            }
        });
    }
    // whichMethod判断使用那种方法
    // 像联网进行数据请求这种耗时操作，最好都是放到子线程中进行，以避免阻塞主线程
    public void sendHttpRequest(final String whichMethod) {
        new Thread(new Runnable() {
            public void run() {
                //这里执行需要联网运行的操作（例  AuthService.getAuth();）
                switch (whichMethod){
                    case "FaceGetUsers":
                        String result = FaceGetUsers.getUsers(classId);
                        studentList = JsonToObejectFun.jsonToObj(result);
                        break;
                    case "FaceGetList":
                        isExist = FaceGetList.isExist_GroupId(classId);
                        break;
                }
            }
        }).start();
    }
    // 捕获返回键的方法2
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(ClassingActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication application = (MyApplication)getApplication();
        classId = application.getClassId();
        studentList = application.getStudentList();
    }
}
