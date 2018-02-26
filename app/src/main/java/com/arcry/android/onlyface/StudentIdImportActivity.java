package com.arcry.android.onlyface;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.arcry.android.onlyface.bean.StudentBean;
import com.baidu.ai.aip.FaceAdd;
import com.baidu.ai.aip.FaceGetUsers;

import java.util.ArrayList;
import java.util.List;
/**
 * 录入新学生信息
 * --学号
 */

public class StudentIdImportActivity extends AppCompatActivity {
    //private EditText studentNameE;
    private EditText studentIdE;
    private Button studentInBtn;
    private String studentId="";
    //private String studentName="";
    private String classId="";
    private String imagePath="";
    //private String result="";
    private boolean isExist = false;
    private boolean isAddSuccess = false;
    private List<StudentBean> studentList;
    private ProgressDialog progressDialog;
    private StudentBean studentBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.studentidimport_layout);
        SysApplication.getInstance().addActivity(this);

        //studentNameE = (EditText) findViewById(R.id.studentName_Edit);
        studentIdE = (EditText) findViewById(R.id.studentId_Edit);
        studentInBtn = (Button) findViewById(R.id.studentInClassBtn);

        studentIdE.setOnEditorActionListener(new EditText.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v.getText().length()!=0 && actionId == EditorInfo.IME_ACTION_GO ) {
                    //处理事件
                    studentInBtn.performClick();
                }
                return false;
            }
        });

        progressDialog = new ProgressDialog(StudentIdImportActivity.this);
        progressDialog.setTitle("");
        progressDialog.setMessage("Loading...");

        studentInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = studentIdE.getText().toString();
                if (TextUtils.isEmpty(num)) {
                    Toast.makeText(StudentIdImportActivity.this, "学号不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    studentId = studentIdE.getText().toString();
                    //studentName = studentNameE.getText().toString();
                    final MyApplication application = (MyApplication)getApplication();
                    Intent get_Intent = getIntent();
                    classId = application.getClassId();
                    imagePath = get_Intent.getStringExtra("imageUri");
                    studentList = application.getStudentList();

                    sendHttpRequest("isExist_UserId");
                    if (isExist){
                        //已经存在该用户
                    }else{
                        //不存在该用户，把该用户加入到班级里即FaceAdd
                        sendHttpRequest("FaceAdd");
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (isAddSuccess){
                                    progressDialog.dismiss();
                                    studentBean=new StudentBean();
                                    studentBean.setUid(studentId);
                                    if (studentList == null){
                                        studentList = new ArrayList<StudentBean>();
                                    }
                                    studentList.add(studentBean);
                                    application.setStudentList(studentList);
                                    Toast.makeText(StudentIdImportActivity.this,"成功添加新同学",Toast .LENGTH_LONG).show();
                                    Intent intent = new Intent(StudentIdImportActivity.this,ClassingActivity.class);
                                    startActivity(intent);
                                }
                            }
                        }, 7000);//秒后执行Runnable中的run方法
                    }
                }
            }
        });
    }
    // whichMethod判断使用那种方法
    // 像联网进行数据请求这种耗时操作，最好都是放到子线程中进行，以避免阻塞主线程
    public void sendHttpRequest(final String whichMethod) {
        new Thread(new Runnable() {
            public void run() {
                switch (whichMethod){
                    case "isExist_UserId":
                        isExist = FaceGetUsers.isExist_UserId(studentId,classId);
                        break;
                    case "FaceAdd":
                        isAddSuccess=FaceAdd.isAddSuccess(imagePath,studentId,classId);
                        break;
                }
            }
        }).start();
    }
    // 捕获返回键的方法2
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(StudentIdImportActivity.this, ClassingActivity.class);
        startActivity(intent);
    }
}


