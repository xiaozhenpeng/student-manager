package com.shu.studentmanager.adpater;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.shu.studentmanager.R;
import com.shu.studentmanager.entity.Student;
import com.shu.studentmanager.entity.Teacher;

import java.util.ArrayList;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.ViewHolder> {
    Context context;
    ArrayList<Teacher> teacherList;

    public TeacherAdapter(Context context) {
        this.context = context;
        this.teacherList = new ArrayList<Teacher>();
    }

    public TeacherAdapter(Context context, ArrayList<Teacher> teacherList) {
        this.context = context;
        this.teacherList = teacherList;
    }

    public TeacherAdapter() {
        this.teacherList = new ArrayList<Teacher>();
    }

    @NonNull
    @Override
    public TeacherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_teacher,parent,false);
        return new TeacherAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(teacherList != null && teacherList.size() >0){
            Teacher teacherEntity = teacherList.get(position);
            holder.teacher_id.setText(teacherEntity.getTid());
            holder.teacher_name.setText(teacherEntity.getTname());
            holder.teacher_password.setText(teacherEntity.getPassword());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public void onClick(View view) {
                    showAlertDialogMode(position,holder.teacher_password);
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    showAlertDialoDelete(position);
                    return true;
                }
            });
        }
    }

    private void showAlertDialoDelete(int position) {
        new MaterialAlertDialogBuilder(context)
                .setTitle("确认")
                .setMessage("确定删除该学生？")
                .setNeutralButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: "+which+" "+position);
                        enSureDelete(teacherList.get(position));
                        teacherList.remove(teacherList.get(position));
//                        refreshListView();
                        notifyDataSetChanged();
                    }
                }).show();
    }

    private void enSureDelete(Teacher teacher) {
        Log.d(TAG, "enSureDelete: "+ teacher.toString());
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void showAlertDialogMode(int position, TextView student_password) {
        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setLineHeight(12);
        FrameLayout container = new FrameLayout(context);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = context.getResources().getDimensionPixelSize(R.dimen.fab_margin);
        params.rightMargin = context.getResources().getDimensionPixelSize(R.dimen.fab_margin);
        input.setLayoutParams(params);
        container.addView(input);
        new MaterialAlertDialogBuilder(context)
                .setTitle("修改教师用户密码")
                .setMessage("输入新密码：")
                .setView(container)
                .setNeutralButton("取消",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "onClick: "+ which +" "+position+" "+input.getText().toString());
                        enSureChangePassword(input.getText().toString(),teacherList.get(position));
                        student_password.setText(input.getText().toString());
                    }
                }).show();
    }

    private void enSureChangePassword(String toString, Teacher teacher) {
        //TODO
    }

    @Override
    public int getItemCount() {
        return teacherList.size();
    }

    public  void updateStudentList(ArrayList<Teacher> mTeacherList){
        this.teacherList.clear();
        this.teacherList.addAll(mTeacherList);
        notifyDataSetChanged();
    }

    public void refreshListView(){
        ArrayList<Teacher> refreshteacherList = new ArrayList<Teacher>();
        refreshteacherList = teacherList;
        updateStudentList(refreshteacherList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView teacher_id, teacher_name, teacher_password;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            teacher_id = itemView.findViewById(R.id.manage_teacher_id_tid);
            teacher_name = itemView.findViewById(R.id.manage_teacher_name_tname);
            teacher_password = itemView.findViewById(R.id.manage_teacher_password_tpassword);
        }
    }
}
