package com.shu.studentmanager.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.system.StructUtsname;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.shu.studentmanager.R;
import com.shu.studentmanager.StudentManagerApplication;
import com.shu.studentmanager.constant.RequestConstant;
import com.shu.studentmanager.databinding.ActivityLoginBinding;
import com.shu.studentmanager.entity.Student;
import com.shu.studentmanager.entity.Teacher;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private ActivityLoginBinding activityLoginBinding;
    private Button btn_login;
    private TextInputEditText text_username;
    private TextInputEditText text_password;
    private RadioGroup radioGroup;
    private String user_kind;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
        activityLoginBinding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(activityLoginBinding.getRoot());
        btn_login = activityLoginBinding.logInBtn;
        text_username = activityLoginBinding.usernameText;
        text_password = activityLoginBinding.passwordText;
        radioGroup = activityLoginBinding.selectKindUser;
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == RequestConstant.REQUEST_SUCCESS){
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtras(msg.getData());
                    startActivity(intent);
                    finish();
                }
            }
        };
        initListener();
    }
    private void initListener(){
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    login(text_username.getText().toString(), text_password.getText().toString());
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.select_teacher:
                        user_kind = "teacher";
                        break;
                    case R.id.select_student:
                        user_kind = "student";
                        break;
                    default:
                        break;
                }
            }
        });
    }
    private void login(String username, String password) throws IOException, JSONException {
//        Log.d(TAG, "login: "+user_kind);
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        if( user_kind == "teacher"){
            json.put("tid",username);
        }else if(user_kind == "student"){
            json.put("sid",username);
        }
        json.put("password",password);
        String url = "http://101.35.20.64:10086/" + user_kind + "/login";
        new Thread(){
          @Override
          public void run(){
              super.run();
              Message message = handler.obtainMessage();
              Bundle bundle = new Bundle();
              OkHttpClient client = new OkHttpClient().newBuilder()
                      .build();
              MediaType mediaType = MediaType.parse("application/json");
              RequestBody body = RequestBody.create(JSON, json.toString());
              Request request = new Request.Builder()
                      .url(url)
                      .method("POST", body)
                      .addHeader("Content-Type", "application/json;charset=utf-8")
                      .build();
              Response response = null;
              try {
                  response = client.newCall(request).execute();
                  if (response.isSuccessful()) {
//                      Log.d(TAG, "run: "+response.body().string());
                      Boolean login_true = Boolean.parseBoolean(response.body().string());

                      if(login_true){
                          Log.d(TAG, "UserLogin: success");
                          message.what = RequestConstant.REQUEST_SUCCESS;
                          bundle.putString("UserKind",user_kind);
                          load(username);
                      } else {
                          message.what = RequestConstant.REQUEST_FAILURE;
                      }
                  }
              } catch (IOException e) {
                  e.printStackTrace();
              }
              message.setData(bundle);
              handler.sendMessage(message);
          }
        }.start();
    }
    private void load(String username) throws IOException {
        String url = "http://101.35.20.64:10086/" + user_kind + "/findById/" + username;
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(url)
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            if(user_kind == "student"){
                Student student = new Gson().fromJson(response.body().string(), Student.class);
                Log.d(TAG, "load: "+student.toString());
                StudentManagerApplication application =(StudentManagerApplication) getApplication();
                application.setId(student.getSid());
                application.setName(student.getSname());
                application.setToken(true);
                application.setType("student");
            } else if(user_kind == "teacher"){
                Teacher teacher = new Gson().fromJson(response.body().string(), Teacher.class);
                Log.d(TAG, "load: "+teacher.toString());
                StudentManagerApplication application =(StudentManagerApplication) getApplication();
                application.setId(teacher.getTid());
                application.setName(teacher.getTname());
                application.setToken(true);
                application.setType("teacher");
            }
        }
        load_select_ok();
        load_current_term();
    }
    private void load_select_ok() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://101.35.20.64:10086/info/getCurrentTerm")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
//                      Log.d(TAG, "run: "+response.body().string());
            Boolean forbidCourseSelection = Boolean.parseBoolean(response.body().string());
            StudentManagerApplication application =(StudentManagerApplication) getApplication();
            application.setForbidCourseSelection(forbidCourseSelection);
        }
    }
    private void load_current_term() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url("http://101.35.20.64:10086/info/getCurrentTerm")
                .method("GET", null)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
//                      Log.d(TAG, "run: "+response.body().string());
//            Boolean forbidCourseSelection = Boolean.parseBoolean(response.body().string());
            StudentManagerApplication application =(StudentManagerApplication) getApplication();
            application.setCurrentTerm(response.body().string());
        }
    }}