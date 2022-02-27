package com.shu.studentmanager.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shu.studentmanager.R;
import com.shu.studentmanager.adpater.CourseManageAdapter;
import com.shu.studentmanager.adpater.CourseOpenAdapter;
import com.shu.studentmanager.databinding.ManageCourseFragmentBinding;
import com.shu.studentmanager.databinding.ManageStudentFragmentBinding;
import com.shu.studentmanager.entity.CourseTeacher;
import com.shu.studentmanager.viewmodel.ManageCourseViewModel;
import com.shu.studentmanager.viewmodel.OpenClassViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ManageCourseFragment extends Fragment {

    private ManageCourseViewModel manageCourseViewModel;
    private ManageCourseFragmentBinding manageCourseFragmentBinding;
    private RecyclerView course_manage_list_recycleview;
    private CourseManageAdapter courseManageAdapter;
    public static OpenClassFragment newInstance() {
        return new OpenClassFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        manageCourseViewModel = new ViewModelProvider(this).get(ManageCourseViewModel.class);
        manageCourseFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.manage_course_fragment,container,false);
        manageCourseFragmentBinding.setManageCourseViewModel(manageCourseViewModel);
        manageCourseFragmentBinding.setLifecycleOwner(getActivity());
        course_manage_list_recycleview = manageCourseFragmentBinding.manageCourseFragmentCourseListRecycleview;
        setCourseOpenListRecycleview();
        setEditTextChangedListener();
        View root = manageCourseFragmentBinding.getRoot();
        try {
            initCourseList();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return root;
//        return inflater.inflate(R.layout.open_class_fragment, container, false);
    }

    private void setEditTextChangedListener() {
        manageCourseFragmentBinding.manageCourseFragmentInputClassNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    initCourseList();
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
        manageCourseFragmentBinding.manageCourseFragmentCourseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    initCourseList();
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
        manageCourseFragmentBinding.manageCourseFragmentCourseCreditHigh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    initCourseList();
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
        manageCourseFragmentBinding.manageCourseFragmentCourseCreditLow.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    initCourseList();
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
        manageCourseFragmentBinding.manageCourseCheckboxIsFuzzy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    initCourseList();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setCourseOpenListRecycleview() {
        course_manage_list_recycleview.setHasFixedSize(true);
        course_manage_list_recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        courseManageAdapter = new CourseManageAdapter(getActivity());
        course_manage_list_recycleview.setAdapter(courseManageAdapter);
        manageCourseViewModel.getMutableLiveData_course_list().observe(getActivity(), new Observer<ArrayList<CourseTeacher>>() {
            @Override
            public void onChanged(ArrayList<CourseTeacher> courseTeachers) {
                courseManageAdapter.updateCourseList(courseTeachers);
            }
        });

    }

    private void initCourseList() throws IOException, JSONException {
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("cid",manageCourseFragmentBinding.manageCourseFragmentInputClassNumber.getText().toString());
        json.put("cname",manageCourseFragmentBinding.manageCourseFragmentCourseName.getText().toString());
        json.put("fuzzy",manageCourseFragmentBinding.manageCourseCheckboxIsFuzzy.isChecked());
        json.put("highBound",manageCourseFragmentBinding.manageCourseFragmentCourseCreditHigh.getText().toString());
        json.put("lowBound",manageCourseFragmentBinding.manageCourseFragmentCourseCreditLow.getText().toString());
//        Log.d(TAG, "initCourseList: "+ json.toString() );
        new Thread(){
            @Override
            public void run(){
                super.run();
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(JSON,json.toString());
                Request request = new Request.Builder()
                        .url("http://101.35.20.64:10086/course/findBySearch")
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
                    ArrayList<CourseTeacher> tlist = null;
                    try {
//                        Log.d(TAG, "run: "+response.body().string());
                        tlist = new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<CourseTeacher>>() {}.getType());
                        //                        mlist.addAll(tlist);

                        manageCourseViewModel.setMutableLiveData_course_list(tlist);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    @Override
    public void onResume() {
//        mViewModel = new ViewModelProvider(this).get(TeacherViewModel.class);
        // TODO: Use the ViewModel
        super.onResume();
        try {
            initCourseList();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        manageCourseFragmentBinding = null;
    }


}