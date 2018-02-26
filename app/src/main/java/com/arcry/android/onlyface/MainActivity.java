package com.arcry.android.onlyface;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.arcry.android.onlyface.utils.JsonToObejectFun;
import com.baidu.ai.aip.FaceGetList;
import com.baidu.ai.aip.FaceGetUsers;

import java.util.List;
/**
 * APP首Activity
 *
 * */

public class MainActivity extends AppCompatActivity {

    private Button classIn_btn;
    private EditText classIn_edit;
    private String classId="";
    private boolean isExist = false;
    private List<StudentBean> studentList;
    private ProgressDialog progressDialog;
    private String access_token="";

    // 定义一个变量，来标识是否退出
    private static boolean isExit = false;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        SysApplication.getInstance().addActivity(this);
        //状态栏优化
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }

        classIn_btn = (Button) findViewById(R.id.classId_Button);
        classIn_edit = (EditText) findViewById(R.id.classId_EditText);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("");
        progressDialog.setMessage("Loading...");

        classIn_edit.setOnEditorActionListener(new EditText.OnEditorActionListener(){
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (v.getText().length()!=0 && actionId == EditorInfo.IME_ACTION_GO ) {
                    //处理事件
                    classIn_btn.performClick();
                }
                return false;
            }
        });

        classIn_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = classIn_edit.getText().toString();
                if (TextUtils.isEmpty(num)) {
                    Toast.makeText(MainActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.show();
                    classId = classIn_edit.getText().toString();
                    MyApplication application = (MyApplication)getApplication();
                    application.setClassId(classId);
                    sendHttpRequest("FaceGetList");

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(isExist){
                                final MyApplication application = (MyApplication)getApplication();
                                sendHttpRequest("FaceGetUsers");
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent inClassIntent = new Intent(MainActivity.this,ClassingActivity.class);
                                        application.setClassId(classId);
                                        application.setStudentList(studentList);
                                        progressDialog.dismiss();
                                        startActivity(inClassIntent);
                                    }
                                }, 5000);//秒后执行Runnable中的run方法
                            }else{
                                //没有该班级号的情况
                                progressDialog.dismiss();
                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                dialog.setTitle("新建新班级");
                                dialog.setMessage("该班级号不存在是否新建该班级并添加同学？");
                                dialog.setCancelable(true);
                                dialog.setPositiveButton("是，新建！", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent addclass = new Intent(MainActivity.this,StudentInActivity.class);
                                        startActivity(addclass);
                                    }
                                });
                                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                dialog.show();
                            }
                        }
                    }, 5000);//5秒后执行Runnable中的run方法
                }
            }
        });
    }

    //Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        //退出
        if ((Intent.FLAG_ACTIVITY_CLEAR_TOP & intent.getFlags()) != 0) {
            finish();
        }
    }
    //whichMethod判断使用那种方法
     //像联网进行数据请求这种耗时操作，最好都是放到子线程中进行，以避免阻塞主线程
    public void sendHttpRequest(final String whichMethod) {
        new Thread(new Runnable() {
            public void run() {
                //这里执行需要联网运行的操作（例  AuthService.getAuth();）
                switch (whichMethod){
                    case "FaceGetList":
                        isExist = FaceGetList.isExist_GroupId(classId);
                        break;
                    case "FaceGetUsers":
                        String result = FaceGetUsers.getUsers(classId);
                        studentList = JsonToObejectFun.jsonToObj(result);
                        break;
                }
            }
        }).start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            SysApplication.getInstance().exit();
        }
    }

}
