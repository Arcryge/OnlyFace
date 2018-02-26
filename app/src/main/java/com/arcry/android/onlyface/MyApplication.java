package com.arcry.android.onlyface;

import android.app.Application;

import com.arcry.android.onlyface.bean.StudentBean;

import java.util.List;

/**
 * Created by Arcry on 2018/1/9.
 */

public final class MyApplication extends Application {
    public List<StudentBean> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentBean> studentList) {
        this.studentList = studentList;
    }



    private List<StudentBean> studentList;
    private String classId;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
