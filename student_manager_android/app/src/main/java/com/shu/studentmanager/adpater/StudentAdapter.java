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

import java.util.ArrayList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    Context context;
    ArrayList<Student> studentList;

    public StudentAdapter() {
        this.studentList = new ArrayList<Student>();
    }

    public StudentAdapter(Context context) {
        this.context = context;
        this.studentList = new ArrayList<Student>();
    }

    public StudentAdapter(Context context, ArrayList<Student> studentList) {
        this.context = context;
        this.studentList = studentList;
    }
    @NonNull
    @Override
    public StudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(studentList != null && studentList.size() >0){
            Student studentEntity = studentList.get(position);
            holder.student_id.setText(studentEntity.getSid());
            holder.student_name.setText(studentEntity.getSname());
            holder.student_password.setText(studentEntity.getPassword());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public void onClick(View view) {
                    showAlertDialogMode(position,holder.student_password);
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
                        enSureDelete(studentList.get(position));
                        studentList.remove(studentList.get(position));
//                        refreshListView();
                        notifyDataSetChanged();
                    }
                }).show();
    }

    private void enSureDelete(Student student) {
        Log.d(TAG, "enSureDelete: "+ student.toString());
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
                .setTitle("修改学生用户密码")
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
                        enSureChangePassword(input.getText().toString(),studentList.get(position));
                        student_password.setText(input.getText().toString());
                    }
                }).show();
    }

    private void enSureChangePassword(String toString, Student student) {
        //TODO
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public  void updateStudentList(ArrayList<Student> mStudentList){
        this.studentList.clear();
        this.studentList.addAll(mStudentList);
        notifyDataSetChanged();
    }

    public void refreshListView(){
        ArrayList<Student> refreshstudentList = new ArrayList<Student>();
        refreshstudentList = studentList;
        updateStudentList(refreshstudentList);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView student_id, student_name, student_password;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            student_id = itemView.findViewById(R.id.manage_student_id_sid);
            student_name = itemView.findViewById(R.id.manage_student_name_sname);
            student_password = itemView.findViewById(R.id.manage_student_password_spassword);
        }
    }
}
