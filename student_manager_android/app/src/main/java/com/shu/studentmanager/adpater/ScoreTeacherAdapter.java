package com.shu.studentmanager.adpater;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
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
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.shu.studentmanager.R;
import com.shu.studentmanager.activity.MainActivity;
import com.shu.studentmanager.constant.RequestConstant;
import com.shu.studentmanager.entity.ScoreTeacher;
import com.shu.studentmanager.fragment.ScoreManageFragment;
import com.shu.studentmanager.viewmodel.ScoreManageViewModel;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ScoreTeacherAdapter extends RecyclerView.Adapter<ScoreTeacherAdapter.ViewHolder> {
    Context context;
    ArrayList<ScoreTeacher> scoreTeacherArrayList;

    public ScoreTeacherAdapter(Context context, ArrayList<ScoreTeacher> scoreTeacherArrayList) {
        this.context = context;
        this.scoreTeacherArrayList = scoreTeacherArrayList;
    }

    public ScoreTeacherAdapter(Context context) {
        this.scoreTeacherArrayList = new ArrayList<ScoreTeacher>();
        this.context = context;
    }

    public ScoreTeacherAdapter() {
        this.scoreTeacherArrayList = new ArrayList<ScoreTeacher>();
    }

    @NonNull
    @Override
    public ScoreTeacherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score_manage,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScoreTeacherAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(scoreTeacherArrayList != null && scoreTeacherArrayList.size()>0){
            ScoreTeacher scoreTeacherEntity = scoreTeacherArrayList.get(position);
            holder.score_manage_study_term.setText(scoreTeacherEntity.getTerm());
            holder.score_manage_student_grade.setText(scoreTeacherEntity.getGrade());
            holder.score_manage_student_sname.setText(scoreTeacherEntity.getSname());
            holder.score_manage_student_sid.setText(scoreTeacherEntity.getSid());
            holder.score_manage_course_id_cid.setText(scoreTeacherEntity.getCid());
            holder.score_manage_course_name_cname.setText(scoreTeacherEntity.getCname());
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.P)
                @Override
                public boolean onLongClick(View view) {
                    showAlertDialog(position,holder.score_manage_student_grade);
                    return true;
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    private void showAlertDialog(int position, TextView score_manage_student_grade) {
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
                .setTitle("修改成绩")
                .setMessage("输入新成绩：")
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
                        enSureChange(input.getText().toString(),scoreTeacherArrayList.get(position));
                        score_manage_student_grade.setText(input.getText().toString());
                    }
                }).show();
    }

    private void enSureChange(String input, ScoreTeacher scoreTeacher) {
        String url = "http://101.35.20.64:10086/SCT/updateById/" + scoreTeacher.getSid() + '/' + scoreTeacher.getCid() + '/'
                        + scoreTeacher.getTid() + '/' + scoreTeacher.getTerm() + '/' + input;
        new Thread(){
            @Override
            public void run(){
                super.run();
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                Request request = new Request.Builder()
                        .url(url)
                        .method("GET", null)
                        .build();
                try {
                    Response response = client.newCall(request).execute();
                    if (response.isSuccessful()) {
//                      Log.d(TAG, "run: "+response.body().string());
                        Boolean insert_true = Boolean.parseBoolean(response.body().string());
                        final MainActivity mainActivity;
                        mainActivity = (MainActivity) context;
                        if(insert_true){
                            Handler handler = mainActivity.getHandler_main_activity();
                            Message message = handler.obtainMessage();
                            message.what = RequestConstant.REQUEST_SUCCESS;
                            handler.sendMessage(message);
                        } else {
                            Handler handler = mainActivity.getHandler_main_activity();
                            Message message = handler.obtainMessage();
                            message.what = RequestConstant.REQUEST_FAILURE;
                            handler.sendMessage(message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public int getItemCount() {
        return scoreTeacherArrayList.size();
    }

    public void updateScoreTeacherList(ArrayList<ScoreTeacher> mScoreTeacherList){
        this.scoreTeacherArrayList.clear();
        this.scoreTeacherArrayList.addAll(mScoreTeacherList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView score_manage_course_id_cid,score_manage_course_name_cname,
                score_manage_student_sid,score_manage_student_sname,
                score_manage_student_grade,score_manage_study_term;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            score_manage_course_id_cid = itemView.findViewById(R.id.score_manage_course_id_cid);
            score_manage_course_name_cname = itemView.findViewById(R.id.score_manage_course_name_cname);
            score_manage_student_sid = itemView.findViewById(R.id.score_manage_student_sid);
            score_manage_student_sname = itemView.findViewById(R.id.score_manage_student_sname);
            score_manage_student_grade = itemView.findViewById(R.id.score_manage_student_grade);
            score_manage_study_term = itemView.findViewById(R.id.score_manage_study_term);
        }
    }

}
