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
import com.shu.studentmanager.adpater.CourseOpenedAdapter;
import com.shu.studentmanager.adpater.CourseOpenedAdapter;
import com.shu.studentmanager.databinding.OpenedFragmentBinding;
import com.shu.studentmanager.databinding.OpenedFragmentBinding;
import com.shu.studentmanager.entity.CourseStudent;
import com.shu.studentmanager.viewmodel.OpenedViewModel;
import com.shu.studentmanager.viewmodel.OpenedViewModel;
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

public class OpenedFragment extends Fragment {

    private OpenedViewModel openedViewModel;
    private OpenedFragmentBinding openedFragmentBinding;
    private RecyclerView course_opened_list_recycleview;
    private CourseOpenedAdapter courseOpenedAdapter;
//    private OpenedViewModel openedViewModel;
//    private OpenedFragmentBinding openedFragmentBinding;
//    private RecyclerView course_opened_list_recycleview;
//    private CourseOpenedAdapter courseOpenedAdapter;

    public static OpenedFragment newInstance() {
        return new OpenedFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        openedViewModel = new ViewModelProvider(this).get(OpenedViewModel.class);
        openedFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.opened_fragment,container,false);
        openedFragmentBinding.setOpenedViewModel(openedViewModel);
        openedFragmentBinding.setLifecycleOwner(getActivity());
        course_opened_list_recycleview = openedFragmentBinding.openedCourseFragmentCourseListRecycleview;
        setCourseSelectListRecycleview();
        setEditTextChangedListener();
        View root = openedFragmentBinding.getRoot();
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
        json.put("cid",openedFragmentBinding.openedCourseFragmentInputClassNumber.getText().toString());
        json.put("cname",openedFragmentBinding.openedCourseFragmentCourseName.getText().toString());
        json.put("tid",openedFragmentBinding.openedCourseFragmentTeacherNumberTid.getText().toString());
        json.put("tname",openedFragmentBinding.openedCourseFragmentTeacherNameTname.getText().toString());
        json.put("tFuzzy",openedFragmentBinding.openedCourseCheckboxIsFuzzy.isChecked());
        json.put("cFuzzy",openedFragmentBinding.openedCourseCheckboxIsFuzzy.isChecked());
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

                        openedViewModel.setMutableLiveData_student_select_course_list(tlist);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    private void setEditTextChangedListener() {
        openedFragmentBinding.openedCourseFragmentInputClassNumber.addTextChangedListener(new TextWatcher() {
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
        openedFragmentBinding.openedCourseFragmentCourseName.addTextChangedListener(new TextWatcher() {
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
        openedFragmentBinding.openedCourseFragmentTeacherNumberTid.addTextChangedListener(new TextWatcher() {
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
        openedFragmentBinding.openedCourseFragmentTeacherNameTname.addTextChangedListener(new TextWatcher() {
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
        openedFragmentBinding.openedCourseCheckboxIsFuzzy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        course_opened_list_recycleview.setHasFixedSize(true);
        course_opened_list_recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        courseOpenedAdapter = new CourseOpenedAdapter(getActivity());
        course_opened_list_recycleview.setAdapter(courseOpenedAdapter);
        openedViewModel.getMutableLiveData_student_select_course_list().observe(getActivity(), new Observer<ArrayList<CourseStudent>>() {
            @Override
            public void onChanged(ArrayList<CourseStudent> courseStudents) {
                courseOpenedAdapter.updateCourseSelectList(courseStudents);
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
        openedFragmentBinding = null;
    }
}