package com.arcry.android.onlyface;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arcry.android.onlyface.bean.StudentBean;

import java.util.List;

/**
 * 课堂考察情况
 * 列出未签到人员名单
 */
public class ClassCaseActivity extends AppCompatActivity {
    private RecyclerView myRV;
    private List<StudentBean> studentList;
    private Button faceSignGoOn_btn;
    private Button finishClass_btn;
    private TextView stdNoInClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classcase_layout);
        SysApplication.getInstance().addActivity(this);
        faceSignGoOn_btn = (Button)findViewById(R.id.faceSign_go_on);
        finishClass_btn = (Button) findViewById(R.id.finish_class);
        stdNoInClass = (TextView) findViewById(R.id.studentNotInClass);
        MyApplication application = (MyApplication)getApplication();
        studentList = application.getStudentList();

        initData();
        myRV = (RecyclerView)findViewById(R.id.studentList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        myRV.setLayoutManager(layoutManager);
        myRV.setAdapter(new StudentAdapter(studentList));
        faceSignGoOn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassCaseActivity.this,SignActivity.class);
                startActivity(intent);
            }
        });
        finishClass_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SysApplication.getInstance().exit();
            }
        });

    }
    protected void initData()
    {
        MyApplication application = (MyApplication) getApplication();
        studentList = application.getStudentList();
    }
    // 捕获返回键的方法2
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
