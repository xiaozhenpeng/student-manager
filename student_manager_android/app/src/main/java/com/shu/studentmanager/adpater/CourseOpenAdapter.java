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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shu.studentmanager.R;
import com.shu.studentmanager.StudentManagerApplication;
import com.shu.studentmanager.activity.MainActivity;
import com.shu.studentmanager.constant.RequestConstant;
import com.shu.studentmanager.entity.CourseTeacher;
import com.shu.studentmanager.fragment.OpenClassFragment;
import com.shu.studentmanager.viewmodel.OpenClassViewModel;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CourseOpenAdapter extends RecyclerView.Adapter<CourseOpenAdapter.ViewHolder> {
    Context context;
    ArrayList<CourseTeacher> courseOpenList;

    public CourseOpenAdapter(Context context, ArrayList<CourseTeacher> courseOpenList) {
        this.context = context;
        this.courseOpenList = courseOpenList;
    }

    public CourseOpenAdapter(){
        this.courseOpenList = new ArrayList<CourseTeacher>();
    }

    public CourseOpenAdapter(Context context) {
        this.context = context;
        this.courseOpenList = new ArrayList<CourseTeacher>();
    }

    @NonNull
    @Override
    public CourseOpenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_open_course,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseOpenAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(courseOpenList != null && courseOpenList.size()>0){
            CourseTeacher courseEntity = courseOpenList.get(position);
            holder.course_id_cid.setText(courseEntity.getCid());
            holder.course_name_cname.setText(courseEntity.getCname());
            holder.course_credit_ccredit.setText(courseEntity.getCcredit());
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new MaterialAlertDialogBuilder(context)
                            .setTitle("确认")
                            .setMessage("确定开设该课程？")
                            .setNeutralButton("取消",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d(TAG, "onClick: "+which+" "+position);
                                    enSureOpen(courseOpenList.get(position));
                                }
                            }).show();
                }
            });
        } else{
            return;
        }
    }

    private void enSureOpen(CourseTeacher courseTeacher) {
        Log.d(TAG, "enSureOpen: "+ courseTeacher.toString());
        StudentManagerApplication application =(StudentManagerApplication) context.getApplicationContext();
        String url = "http://101.35.20.64:10086/courseTeacher/insert/"+courseTeacher.getCid()+"/"+ application.getId() +"/"+application.getCurrentTerm();
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
        return courseOpenList.size();
    }

    public void updateCourseList(ArrayList<CourseTeacher> mcoursesList){
        this.courseOpenList.clear();
        this.courseOpenList.addAll(mcoursesList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView course_credit_ccredit, course_name_cname, course_id_cid;
        Button button;
        public ViewHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.course_open_operation);
            course_id_cid = itemView.findViewById(R.id.course_open_id_cid);
            course_name_cname = itemView.findViewById(R.id.course_open_name_cname);
            course_credit_ccredit = itemView.findViewById(R.id.course_open_credit_ccredit) ;
            button = itemView.findViewById(R.id.course_open_operation);
        }
    }

}
