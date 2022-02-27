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
import com.shu.studentmanager.adpater.StudentAdapter;
import com.shu.studentmanager.adpater.TeacherAdapter;
import com.shu.studentmanager.databinding.ManageStudentFragmentBinding;
import com.shu.studentmanager.databinding.ManageTeacherFragmentBinding;
import com.shu.studentmanager.entity.Student;
import com.shu.studentmanager.entity.Teacher;
import com.shu.studentmanager.viewmodel.ManageStudentViewModel;
import com.shu.studentmanager.viewmodel.ManageTeacherViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ManageTeacherFragment extends Fragment {

    private ManageTeacherViewModel manageTeacherViewModel;
    private ManageTeacherFragmentBinding manageTeacherFragmentBinding;
    public static ManageStudentFragment newInstance() {
        return new ManageStudentFragment();
    }

    private RecyclerView teacher_list_recyclerview;
    private TeacherAdapter teacherAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        manageTeacherViewModel = new ViewModelProvider(this).get(ManageTeacherViewModel.class);
        manageTeacherFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.manage_teacher_fragment,container,false);
        manageTeacherFragmentBinding.setManageTeacherViewModel(manageTeacherViewModel);
        manageTeacherFragmentBinding.setLifecycleOwner(getActivity());

        teacher_list_recyclerview = manageTeacherFragmentBinding.manageTeacherFragmentTeacherListRecycleview;
        setTeacherListRecycleview();
        setEditTextChangeListener();
        View root = manageTeacherFragmentBinding.getRoot();
        try {
            initTeacherList();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return root;
        //        return inflater.inflate(R.layout.manage_student_fragment, container, false);
    }

    private void initTeacherList() throws JSONException ,IOException {
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        JSONObject json = new JSONObject();
        json.put("tid",manageTeacherFragmentBinding.manageTeacherFragmentTeacherNumberTid.getText().toString());
        json.put("tname",manageTeacherFragmentBinding.manageTeacherFragmentTeacherNameTname.getText().toString());
        json.put("password",manageTeacherFragmentBinding.manageTeacherCheckboxIsFuzzy.isChecked());
        new Thread(){
            @Override
            public void run(){
                super.run();
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(JSON,json.toString());
                Request request = new Request.Builder()
                        .url("http://101.35.20.64:10086/teacher/findBySearch")
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
                    ArrayList<Teacher> tlist = null;
                    try {
//                        Log.d(TAG, "run: "+response.body().string());
                        tlist = new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<Teacher>>() {}.getType());
                        //                        mlist.addAll(tlist);

                        manageTeacherViewModel.setMutableLiveData_manage_teacher_list(tlist);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }.start();
    }

    private void setEditTextChangeListener() {
        manageTeacherFragmentBinding.manageTeacherFragmentTeacherNumberTid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    initTeacherList();
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
        manageTeacherFragmentBinding.manageTeacherFragmentTeacherNameTname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                try {
                    initTeacherList();
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
        manageTeacherFragmentBinding.manageTeacherCheckboxIsFuzzy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    initTeacherList();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setTeacherListRecycleview() {
        teacher_list_recyclerview.setHasFixedSize(true);
        teacher_list_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        teacherAdapter = new TeacherAdapter(getActivity());
        teacher_list_recyclerview.setAdapter(teacherAdapter);
        manageTeacherViewModel.getMutableLiveData_manage_teacher_list().observe(getActivity(), new Observer<ArrayList<Teacher>>() {
            @Override
            public void onChanged(ArrayList<Teacher> teachers) {
                teacherAdapter.updateStudentList(teachers);
            }
        });
    }

    @Override
    public void onResume() {
//        mViewModel = new ViewModelProvider(this).get(TeacherViewModel.class);
        // TODO: Use the ViewModel
        super.onResume();
        try {
            initTeacherList();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        manageTeacherFragmentBinding = null;
    }
}