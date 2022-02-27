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
import com.shu.studentmanager.activity.MainActivity;
import com.shu.studentmanager.constant.RequestConstant;
import com.shu.studentmanager.entity.CourseTeacher;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.ViewHolder> {
    Context context;
    ArrayList<CourseTeacher> coursesList;

    public CourseAdapter(Context context, ArrayList<CourseTeacher> coursesList) {
        this.context = context;
        this.coursesList = coursesList;
    }

    public CourseAdapter(Context context) {
        this.coursesList = new ArrayList<CourseTeacher>();
        this.context = context;
    }

    public CourseAdapter() {
        this.coursesList = new ArrayList<CourseTeacher>();
    }

    @NonNull
    @Override
    public CourseAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(coursesList != null && coursesList.size() > 0){
            CourseTeacher course_entity = coursesList.get(position);
            holder.course_id_cid.setText(course_entity.getCid());
            holder.course_name_cname.setText(course_entity.getCname());
            holder.course_credit_ccredit.setText(course_entity.getCcredit());
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    new MaterialAlertDialogBuilder(context)
                            .setTitle("确认")
                            .setMessage("确定不开设该课程？")
                            .setNeutralButton("取消",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d(TAG, "onClick: "+which+" "+position);
                                    enSureDelete(coursesList.get(position));
                                }
                            }).show();
                    return false;
                }
            });
        } else {
            return;
        }
    }

    private void enSureDelete(CourseTeacher courseTeacher) {
        Log.d(TAG, "enSureDelete: "+ courseTeacher.toString());
        String url = "http://101.35.20.64:10086/course/deleteById/" + courseTeacher.getCid();
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
        return coursesList.size();
    }

    public void updateCourseList(ArrayList<CourseTeacher> mcoursesList){
        this.coursesList.clear();
        this.coursesList.addAll(mcoursesList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView course_credit_ccredit, teacher_name_tname, course_name_cname, course_id_cid;
        Button operation_btn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            course_id_cid = itemView.findViewById(R.id.course_id_cid);
            course_name_cname = itemView.findViewById(R.id.course_name_cname);
            course_credit_ccredit = itemView.findViewById(R.id.course_credit_ccredit) ;
        }
    }

}
