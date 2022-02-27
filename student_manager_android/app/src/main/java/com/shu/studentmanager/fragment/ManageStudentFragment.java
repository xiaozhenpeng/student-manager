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
import com.shu.studentmanager.StudentManagerApplication;
import com.shu.studentmanager.adpater.StudentAdapter;
import com.shu.studentmanager.databinding.ManageStudentFragmentBinding;
import com.shu.studentmanager.entity.CourseTeacher;
import com.shu.studentmanager.entity.Student;
import com.shu.studentmanager.viewmodel.ManageStudentViewModel;
import com.shu.studentmanager.viewmodel.StudentViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ManageStudentFragment extends Fragment {

    private ManageStudentViewModel manageStudentViewModel;
    private ManageStudentFragmentBinding manageStudentFragmentBinding;
    public static ManageStudentFragment newInstance() {
        return new ManageStudentFragment();
    }

    private RecyclerView student_list_recyclerview;
    private StudentAdapter studentAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        manageStudentViewModel = new ViewModelProvider(this).get(ManageStudentViewModel.class);
        manageStudentFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.manage_student_fragment,container,false);
        manageStudentFragmentBinding.setManageStudentViewModel(manageStudentViewModel);
        manageStudentFragmentBinding.setLifecycleOwner(getActivity());

        student_list_recyclerview = manageStudentFragmentBinding.manageStudentFragmentStudentListRecycleview;
        setStudentListRecycleview();
        setEditTextChangeListener();
        View root = manageStudentFragmentBinding.getRoot();
        try {
            initStudentList();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return root;
        //        return inflater.inflate(R.layout.manage_student_fragment, container, false);
    }

    private void initStudentList() throws JSONException ,IOException {
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("sid",manageStudentFragmentBinding.manageStudentFragmentStudentNumberSid.getText().toString());
        json.put("sname",manageStudentFragmentBinding.manageStudentFragmentStudentNameSname.getText().toString());
        json.put("password",manageStudentFragmentBinding.manageStudentCheckboxIsFuzzy.isChecked());
        new Thread(){
            @Override
            public void run(){
                super.run();
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(JSON,json.toString());
                Request request = new Request.Builder()
                        .url("http://101.35.20.64:10086/student/findBySearch")
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
                    ArrayList<Student> tlist = null;
                    try {
//                        Log.d(TAG, "run: "+response.body().string());
                        tlist = new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<Student>>() {}.getType());
                        //                        mlist.addAll(tlist);

                        manageStudentViewModel.setMutableLiveData_manage_student_list(tlist);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    private void setEditTextChangeListener() {
        manageStudentFragmentBinding.manageStudentFragmentStudentNumberSid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    initStudentList();
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
        manageStudentFragmentBinding.manageStudentFragmentStudentNameSname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    initStudentList();
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
        manageStudentFragmentBinding.manageStudentCheckboxIsFuzzy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    initStudentList();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setStudentListRecycleview() {
        student_list_recyclerview.setHasFixedSize(true);
        student_list_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        studentAdapter = new StudentAdapter(getActivity());
        student_list_recyclerview.setAdapter(studentAdapter);
        manageStudentViewModel.getMutableLiveData_manage_student_list().observe(getActivity(), new Observer<ArrayList<Student>>() {
            @Override
            public void onChanged(ArrayList<Student> students) {
                studentAdapter.updateStudentList(students);
            }
        });
    }

    @Override
    public void onResume() {
//        mViewModel = new ViewModelProvider(this).get(TeacherViewModel.class);
        // TODO: Use the ViewModel
        super.onResume();
        try {
            initStudentList();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        manageStudentFragmentBinding = null;
    }


}