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

import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shu.studentmanager.activity.MainActivity;
import com.shu.studentmanager.adpater.CourseOpenAdapter;
import com.shu.studentmanager.constant.RequestConstant;
import com.shu.studentmanager.databinding.OpenClassFragmentBinding;
import com.shu.studentmanager.entity.CourseTeacher;
import com.shu.studentmanager.viewmodel.OpenClassViewModel;
import com.shu.studentmanager.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OpenClassFragment extends Fragment {

    private OpenClassViewModel openClassViewModel;
    private OpenClassFragmentBinding openClassFragmentBinding;
    private RecyclerView course_open_list_recycleview;
    private CourseOpenAdapter courseOpenAdapter;
    public static OpenClassFragment newInstance() {
        return new OpenClassFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        openClassViewModel = new ViewModelProvider(this).get(OpenClassViewModel.class);
        openClassFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.open_class_fragment,container,false);
        openClassFragmentBinding.setOpenClassViewModel(openClassViewModel);
        openClassFragmentBinding.setLifecycleOwner(getActivity());
        course_open_list_recycleview = openClassFragmentBinding.openClassFragmentCourseListRecycleview;
        setCourseOpenListRecycleview();
        setEditTextChangedListener();
        View root = openClassFragmentBinding.getRoot();
        try {
            initCourseList();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return root;
//        return inflater.inflate(R.layout.open_class_fragment, container, false);
    }

    private void setEditTextChangedListener() {
        openClassFragmentBinding.openClassFragmentInputClassNumber.addTextChangedListener(new TextWatcher() {
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
        openClassFragmentBinding.openClassFragmentCourseName.addTextChangedListener(new TextWatcher() {
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
        openClassFragmentBinding.openClassFragmentCourseCreditHigh.addTextChangedListener(new TextWatcher() {
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
        openClassFragmentBinding.openClassFragmentCourseCreditLow.addTextChangedListener(new TextWatcher() {
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
        openClassFragmentBinding.checkboxIsFuzzy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        course_open_list_recycleview.setHasFixedSize(true);
        course_open_list_recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        courseOpenAdapter = new CourseOpenAdapter(getActivity());
        course_open_list_recycleview.setAdapter(courseOpenAdapter);
        openClassViewModel.getMutableLiveData_course_list().observe(getActivity(), new Observer<ArrayList<CourseTeacher>>() {
            @Override
            public void onChanged(ArrayList<CourseTeacher> courseTeachers) {
                courseOpenAdapter.updateCourseList(courseTeachers);
            }
        });

    }

    private void initCourseList() throws IOException, JSONException {
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("cid",openClassFragmentBinding.openClassFragmentInputClassNumber.getText().toString());
        json.put("cname",openClassFragmentBinding.openClassFragmentCourseName.getText().toString());
        json.put("fuzzy",openClassFragmentBinding.checkboxIsFuzzy.isChecked());
        json.put("highBound",openClassFragmentBinding.openClassFragmentCourseCreditHigh.getText().toString());
        json.put("lowBound",openClassFragmentBinding.openClassFragmentCourseCreditLow.getText().toString());
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

                        openClassViewModel.setMutableLiveData_course_list(tlist);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        openClassViewModel = new ViewModelProvider(this).get(OpenClassViewModel.class);
        // TODO: Use the ViewModel
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
        openClassFragmentBinding = null;
    }

}