package com.shu.studentmanager.fragment;

import static android.content.ContentValues.TAG;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shu.studentmanager.R;
import com.shu.studentmanager.adpater.StudentCourseSelectAdapter;
import com.shu.studentmanager.databinding.CourseSelectFragmentBinding;
import com.shu.studentmanager.databinding.OpenClassFragmentBinding;
import com.shu.studentmanager.entity.CourseStudent;
import com.shu.studentmanager.entity.CourseTeacher;
import com.shu.studentmanager.viewmodel.CourseSelectViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CourseSelectFragment extends Fragment {

    private CourseSelectViewModel courseSelectViewModel;
    private CourseSelectFragmentBinding courseSelectFragmentBinding;
    private RecyclerView course_select_list_recycleview;
    private StudentCourseSelectAdapter studentCourseSelectAdapter;

    public static CourseSelectFragment newInstance() {
        return new CourseSelectFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        courseSelectViewModel = new ViewModelProvider(this).get(CourseSelectViewModel.class);
        courseSelectFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.course_select_fragment,container,false);
        courseSelectFragmentBinding.setCourseSelectViewModel(courseSelectViewModel);
        courseSelectFragmentBinding.setLifecycleOwner(getActivity());
        course_select_list_recycleview = courseSelectFragmentBinding.selectCourseFragmentCourseListRecycleview;
        setCourseSelectListRecycleview();
        setEditTextChangedListener();
        View root = courseSelectFragmentBinding.getRoot();
        try {
            initCourseSelectList();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return root;
//        return inflater.inflate(R.layout.course_select_fragment, container, false);
    }

    private void initCourseSelectList() throws JSONException ,IOException{
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("cid",courseSelectFragmentBinding.selectCourseFragmentInputClassNumber.getText().toString());
        json.put("cname",courseSelectFragmentBinding.selectCourseFragmentCourseName.getText().toString());
        json.put("tid",courseSelectFragmentBinding.selectCourseFragmentTeacherNumberTid.getText().toString());
        json.put("tname",courseSelectFragmentBinding.selectCourseFragmentTeacherNameTname.getText().toString());
        json.put("tFuzzy",courseSelectFragmentBinding.selectCourseCheckboxIsFuzzy.isChecked());
        json.put("cFuzzy",courseSelectFragmentBinding.selectCourseCheckboxIsFuzzy.isChecked());
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
                        .url("http://101.35.20.64:10086/courseTeacher/findCourseTeacherInfo")
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
//                        Log.d(TAG, "run: "+response.body().string());
                        tlist = new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<CourseStudent>>() {}.getType());
                        //                        mlist.addAll(tlist);

                        courseSelectViewModel.setMutableLiveData_student_select_course_list(tlist);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    private void setEditTextChangedListener() {
        courseSelectFragmentBinding.selectCourseFragmentInputClassNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    initCourseSelectList();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        courseSelectFragmentBinding.selectCourseFragmentCourseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    initCourseSelectList();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        courseSelectFragmentBinding.selectCourseFragmentTeacherNumberTid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    initCourseSelectList();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        courseSelectFragmentBinding.selectCourseFragmentTeacherNameTname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    initCourseSelectList();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        courseSelectFragmentBinding.selectCourseCheckboxIsFuzzy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    initCourseSelectList();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setCourseSelectListRecycleview() {
        course_select_list_recycleview.setHasFixedSize(true);
        course_select_list_recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        studentCourseSelectAdapter = new StudentCourseSelectAdapter(getActivity());
        course_select_list_recycleview.setAdapter(studentCourseSelectAdapter);
        courseSelectViewModel.getMutableLiveData_student_select_course_list().observe(getActivity(), new Observer<ArrayList<CourseStudent>>() {
            @Override
            public void onChanged(ArrayList<CourseStudent> courseStudents) {
                studentCourseSelectAdapter.updateCourseSelectList(courseStudents);
            }
        });
    }

    @Override
    public void onResume() {
//        mViewModel = new ViewModelProvider(this).get(TeacherViewModel.class);
        // TODO: Use the ViewModel
        super.onResume();
        try {
            initCourseSelectList();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        courseSelectFragmentBinding = null;
    }

}