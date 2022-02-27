package com.shu.studentmanager.adpater;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.shu.studentmanager.R;
import com.shu.studentmanager.entity.CourseStudent;
import com.shu.studentmanager.entity.CourseTeacher;

import java.util.ArrayList;

public class StudentCourseAdapter extends RecyclerView.Adapter<StudentCourseAdapter.ViewHolder> {
    Context context;
    ArrayList<CourseStudent> courseStudentList;

    public StudentCourseAdapter(Context context) {
        this.context = context;
        this.courseStudentList = new ArrayList<CourseStudent>();
    }

    public StudentCourseAdapter() {
        this.courseStudentList = new ArrayList<CourseStudent>();
    }

    public StudentCourseAdapter(Context context, ArrayList<CourseStudent> courseStudentList) {
        this.context = context;
        this.courseStudentList = courseStudentList;
    }

    @NonNull
    @Override
    public StudentCourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student_course,parent,false);
        return new StudentCourseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentCourseAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(courseStudentList != null && courseStudentList.size() > 0){
            CourseStudent courseStudentEntity = courseStudentList.get(position);
            holder.student_course_cid.setText(courseStudentEntity.getCid());
            holder.student_course_cname.setText(courseStudentEntity.getCname());
            holder.student_course_tid.setText(courseStudentEntity.getTid());
            holder.student_course_tname.setText(courseStudentEntity.getTname());
            holder.student_course_ccredit.setText(courseStudentEntity.getCcredit());
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new MaterialAlertDialogBuilder(context)
                            .setTitle("确认")
                            .setMessage("确定不选该课程？")
                            .setNeutralButton("取消",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d(TAG, "onClick: "+which+" "+position);
                                    enSureDelete(courseStudentList.get(position));
                                }
                            }).show();
                    return false;
                }
            });
        } else {
            return;
        }
    }
    private void enSureDelete(CourseStudent courseStudent) {
        Log.d(TAG, "enSureDelete: "+ courseStudent.toString());

    }

    @Override
    public int getItemCount() {
        return courseStudentList.size();
    }

    public void updateCourseList(ArrayList<CourseStudent> mcoureseList){
        this.courseStudentList.clear();
        this.courseStudentList.addAll(mcoureseList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView student_course_cid, student_course_cname,
                student_course_tid, student_course_tname, student_course_ccredit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            student_course_cid = itemView.findViewById(R.id.student_course_id_cid);
            student_course_cname = itemView.findViewById(R.id.student_course_name_cname);
            student_course_tid = itemView.findViewById(R.id.student_teacher_id_tid);
            student_course_tname = itemView.findViewById(R.id.student_teacher_name_tname);
            student_course_ccredit = itemView.findViewById(R.id.student_course_credit_ccredit);
        }
    }
}
