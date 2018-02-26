package com.arcry.android.onlyface;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.arcry.android.onlyface.bean.StudentBean;

import java.util.List;

/**
 * Created by Arcry on 2018/1/10.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder>{
    private List<StudentBean> mstudentList ;
    static class ViewHolder extends RecyclerView.ViewHolder{
        //String uid;
        //String user_info;
        TextView uid;
        public ViewHolder (View view){
            super(view);
            uid=(TextView)view.findViewById(R.id.id_num);
        }
    }
    public StudentAdapter(List<StudentBean> studentList){
        mstudentList=studentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(StudentAdapter.ViewHolder holder, int position) {
        holder.uid.setText(mstudentList.get(position).getUid());
    }

    @Override
    public int getItemCount() {
        if (mstudentList==null) {
            return 1;
        }
        return mstudentList.size();
    }
}
