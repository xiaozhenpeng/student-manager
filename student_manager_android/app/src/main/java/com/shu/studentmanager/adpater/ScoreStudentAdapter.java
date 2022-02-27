package com.shu.studentmanager.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shu.studentmanager.R;
import com.shu.studentmanager.entity.Course;

import java.util.ArrayList;

public class ScoreStudentAdapter extends RecyclerView.Adapter<ScoreStudentAdapter.ViewHolder> {
    Context context;
    ArrayList<Course> scoreStudentList;

    public ScoreStudentAdapter(Context context) {
        this.context = context;
        this.scoreStudentList = new ArrayList<Course>();
    }

    public ScoreStudentAdapter(Context context, ArrayList<Course> scoreStudentList) {
        this.context = context;
        this.scoreStudentList = scoreStudentList;
    }

    public ScoreStudentAdapter() {
        this.scoreStudentList = new ArrayList<Course>();
    }

    @NonNull
    @Override
    public ScoreStudentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score_review,parent,false);
        return new ScoreStudentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreStudentAdapter.ViewHolder holder, int position) {
        if(scoreStudentList != null && scoreStudentList.size()>0){
            Course courseStudentEntity = scoreStudentList.get(position);
            holder.score_review_course_id_cid.setText(courseStudentEntity.getCid());
            holder.score_review_course_name_cname.setText(courseStudentEntity.getCname());
            holder.score_review_teacher_tid.setText(courseStudentEntity.getTid());
            holder.score_review_teacher_tname.setText(courseStudentEntity.getTname());
            holder.score_review_student_grade.setText(courseStudentEntity.getGrade());
            holder.score_review_study_ccredit.setText(courseStudentEntity.getCcredit());
        }
    }

    @Override
    public int getItemCount() {
        return scoreStudentList.size();
    }

    public void updateScoreStudentList(ArrayList<Course> mScoreStudentList){
        this.scoreStudentList.clear();
        this.scoreStudentList.addAll(mScoreStudentList);
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView score_review_course_id_cid,score_review_course_name_cname,
                score_review_teacher_tid,score_review_teacher_tname,
                score_review_student_grade,score_review_study_ccredit;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            score_review_course_id_cid = itemView.findViewById(R.id.score_review_course_id_cid);
            score_review_course_name_cname = itemView.findViewById(R.id.score_review_course_name_cname);
            score_review_teacher_tid = itemView.findViewById(R.id.score_review_teacher_number_tid);
            score_review_teacher_tname = itemView.findViewById(R.id.score_review_teacher_name_tname);
            score_review_student_grade = itemView.findViewById(R.id.score_review_student_grade);
            score_review_study_ccredit = itemView.findViewById(R.id.score_review_course_ccredit);
        }
    }
}
