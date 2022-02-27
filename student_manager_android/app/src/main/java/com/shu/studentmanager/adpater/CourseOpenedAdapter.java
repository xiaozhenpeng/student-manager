package com.shu.studentmanager.adpater;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.shu.studentmanager.R;
import com.shu.studentmanager.StudentManagerApplication;
import com.shu.studentmanager.activity.MainActivity;
import com.shu.studentmanager.constant.RequestConstant;
import com.shu.studentmanager.entity.CourseStudent;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CourseOpenedAdapter extends RecyclerView.Adapter<CourseOpenedAdapter.ViewHolder> {
    Context context;
    ArrayList<CourseStudent> courseOpenedList;

    public CourseOpenedAdapter(Context context) {
        this.context = context;
        this.courseOpenedList = new ArrayList<CourseStudent>();
    }

    public CourseOpenedAdapter() {
        this.courseOpenedList = new ArrayList<CourseStudent>();
    }

    public CourseOpenedAdapter(Context context, ArrayList<CourseStudent> courseOpenedList) {
        this.context = context;
        this.courseOpenedList = courseOpenedList;
    }

    @NonNull
    @Override
    public CourseOpenedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_opened_course,parent,false);
        return new CourseOpenedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseOpenedAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(courseOpenedList != null && courseOpenedList.size()>0){
            CourseStudent courseStudentEntity = courseOpenedList.get(position);
            holder.student_opened_course_cid.setText(courseStudentEntity.getCid());
            holder.student_opened_course_cname.setText(courseStudentEntity.getCname());
            holder.student_opened_course_tid.setText(courseStudentEntity.getTid());
            holder.student_opened_course_tname.setText(courseStudentEntity.getTname());
            holder.student_opened_course_ccredit.setText(courseStudentEntity.getCcredit());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new MaterialAlertDialogBuilder(context)
                            .setTitle("确认")
                            .setMessage("确定删除该课程？")
                            .setNeutralButton("取消",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d(TAG, "onClick: "+which+" "+position);
                                    enSureDelete(courseOpenedList.get(position));
                                    courseOpenedList.remove(courseOpenedList.get(position));
                                }
                            }).show();
                }
            });
        }
    }

    private void enSureDelete(CourseStudent courseStudent) {
        Log.d(TAG, "enSureDelete: select course");
        Log.d(TAG, "enSureDelete: "+ courseStudent.toString());
        StudentManagerApplication application =(StudentManagerApplication) context.getApplicationContext();
        String url = "http://101.35.20.64:10086/course/deleteById/"+courseStudent.getCid();
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
        return courseOpenedList.size();
    }

    public void updateCourseSelectList(ArrayList<CourseStudent> mcoursesList){
        this.courseOpenedList.clear();
        this.courseOpenedList.addAll(mcoursesList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView student_opened_course_cid, student_opened_course_cname,
                student_opened_course_tid, student_opened_course_tname, student_opened_course_ccredit;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            student_opened_course_cid = itemView.findViewById(R.id.course_opened_id_cid);
            student_opened_course_cname = itemView.findViewById(R.id.course_opened_name_cname);
            student_opened_course_tid = itemView.findViewById(R.id.course_opened_techer_id_tid);
            student_opened_course_tname = itemView.findViewById(R.id.course_opened_techer_name_tname);
            student_opened_course_ccredit = itemView.findViewById(R.id.course_opened_credit_ccredit);
        }
    }
}
