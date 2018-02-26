package com.arcry.android.onlyface.bean;

/**
 * Created by Arcry on 2018/1/9.
 */

public class StudentBean {
    private String uid;
    private String user_info;

    public StudentBean(){}
    public StudentBean(String id,String name){
        this.uid=id;
        this.user_info=name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUser_info() {
        return user_info;
    }

    public void setUser_info(String user_info) {
        this.user_info = user_info;
    }

    public String toString() {
        return "StudentBean{" +
                "uid='" + uid + '\'' +
                ", user_info=" + user_info + '}';
    }
}
