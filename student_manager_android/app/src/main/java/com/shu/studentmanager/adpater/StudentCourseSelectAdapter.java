package com.shu.studentmanager.adpater;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
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
import com.shu.studentmanager.entity.CourseStudent;
import com.shu.studentmanager.entity.CourseTeacher;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class StudentCourseSelectAdapter extends RecyclerView.Adapter<StudentCourseSelectAdapter.ViewHolder> {
    Context context;
    ArrayList<CourseStudent> courseSelectList;

    public StudentCourseSelectAdapter(Context context) {
        this.context = context;
        this.courseSelectList = new ArrayList<CourseStudent>();
    }

    public StudentCourseSelectAdapter() {
        this.courseSelectList = new ArrayList<CourseStudent>();
    }

    public StudentCourseSelectAdapter(Context context, ArrayList<CourseStudent> courseSelectList) {
        this.context = context;
        this.courseSelectList = courseSelectList;
    }

    @NonNull
    @Override
    public StudentCourseSelectAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_select_course,parent,false);
        return new StudentCourseSelectAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentCourseSelectAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(courseSelectList != null && courseSelectList.size()>0){
            CourseStudent courseStudentEntity = courseSelectList.get(position);
            holder.student_select_course_cid.setText(courseStudentEntity.getCid());
            holder.student_select_course_cname.setText(courseStudentEntity.getCname());
            holder.student_select_course_tid.setText(courseStudentEntity.getTid());
            holder.student_select_course_tname.setText(courseStudentEntity.getTname());
            holder.student_select_course_ccredit.setText(courseStudentEntity.getCcredit());
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new MaterialAlertDialogBuilder(context)
                            .setTitle("确认")
                            .setMessage("确定选择该课程？")
                            .setNeutralButton("取消",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setPositiveButton("确认",new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Log.d(TAG, "onClick: "+which+" "+position);
                                    try {
                                        enSureSelect(courseSelectList.get(position));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).show();
                }
            });
        }
    }

    private void enSureSelect(CourseStudent courseStudent) throws JSONException {
        Log.d(TAG, "enSureSelect: select course");
        StudentManagerApplication application =(StudentManagerApplication) context.getApplicationContext();
        Toast.makeText(context,"选课成功",Toast.LENGTH_SHORT);
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("cid",courseStudent.getCid());
        json.put("tid",courseStudent.getTid());
        json.put("sid",application.getId());
        json.put("term",application.getCurrentTerm());
        Log.d(TAG, "initCourseSelectList: "+ json.toString());
        new Thread(){
            @Override
            public void run(){
                super.run();
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(JSON,json.toString());
                Request request = new Request.Builder()
                        .url("http://101.35.20.64:10086/SCT/save")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(response.isSuccessful()){
                    ArrayList<CourseStudent> tlist = null;
                    try {
                        Log.d(TAG, "run: "+response.body().string());
//                        tlist = new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<CourseStudent>>() {}.getType());
                        //                        mlist.addAll(tlist);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    @Override
    public int getItemCount() {
        return courseSelectList.size();
    }

    public void updateCourseSelectList(ArrayList<CourseStudent> mcoursesList){
        this.courseSelectList.clear();
        this.courseSelectList.addAll(mcoursesList);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView student_select_course_cid, student_select_course_cname,
                student_select_course_tid, student_select_course_tname, student_select_course_ccredit;
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.course_select_operation);
            student_select_course_cid = itemView.findViewById(R.id.course_select_id_cid);
            student_select_course_cname = itemView.findViewById(R.id.course_select_name_cname);
            student_select_course_tid = itemView.findViewById(R.id.course_select_techer_id_tid);
            student_select_course_tname = itemView.findViewById(R.id.course_select_techer_name_tname);
            student_select_course_ccredit = itemView.findViewById(R.id.course_select_credit_ccredit);
        }
    }
}
