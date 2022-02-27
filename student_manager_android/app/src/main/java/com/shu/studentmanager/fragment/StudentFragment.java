package com.shu.studentmanager.fragment;

import static com.shu.studentmanager.constant.DurationsKt.MEDIUM_EXPAND_DURATION;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shu.studentmanager.R;
import com.shu.studentmanager.StudentManagerApplication;
import com.shu.studentmanager.adpater.StudentCourseAdapter;
import com.shu.studentmanager.databinding.StudentFragmentBinding;
import com.shu.studentmanager.entity.CourseStudent;
import com.shu.studentmanager.entity.CourseTeacher;
import com.shu.studentmanager.transition.TransitionsKt;
import com.shu.studentmanager.viewmodel.StudentViewModel;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StudentFragment extends Fragment {

    private StudentViewModel studentViewModel;
    public static StudentFragmentBinding studentFragmentBinding;
    public static StudentFragment newInstance() {
        return new StudentFragment();
    }

    private RecyclerView student_course_list_recyclerview;
    private StudentCourseAdapter studentCourseAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        studentFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.student_fragment,container,false);
        studentFragmentBinding.setStudentViewModel(studentViewModel);
        studentFragmentBinding.setLifecycleOwner(getActivity());

        StudentManagerApplication application = (StudentManagerApplication) getActivity().getApplication();
        studentFragmentBinding.studentFragmentStudentId.setText(application.getId());
        studentFragmentBinding.studentFragmentStudentName.setText(application.getName());

        student_course_list_recyclerview = studentFragmentBinding.studentFragmentCourseListRecycleview;
        setStudentCoureListRecycleView();
        View root = studentFragmentBinding.getRoot();
        try {
            initStudentCourseList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
//        return inflater.inflate(R.layout.student_fragment, container, false);
    }

    private void setStudentCoureListRecycleView() {
        student_course_list_recyclerview.setHasFixedSize(true);
        student_course_list_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        studentCourseAdapter = new StudentCourseAdapter(getActivity());
        student_course_list_recyclerview.setAdapter(studentCourseAdapter);
//        studentViewModel.get
        studentViewModel.getMutableLiveData_student_course_list().observe(getActivity(), new Observer<ArrayList<CourseStudent>>() {
            @Override
            public void onChanged(ArrayList<CourseStudent> courseStudents) {
//                Log.d(TAG, "onChanged: update");
                studentFragmentBinding.studentFragmentStudentClassNumber.setText("本学期共有 "+String.valueOf(courseStudents.size())+" 门课");
                studentCourseAdapter.updateCourseList(courseStudents);
            }
        });
    }

    private void initStudentCourseList() throws IOException  {
        StudentManagerApplication application = (StudentManagerApplication) getActivity().getApplication();
        String url = "http://101.35.20.64:10086/SCT/findBySid/"+application.getId()+"/"+application.getCurrentTerm();
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
//                    Log.d(TAG, "run: "+response.body().string());
                    if(response.isSuccessful()){
                        ArrayList<CourseStudent> tlist = new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<CourseStudent>>() {}.getType());
//                        mlist.addAll(tlist);
                        studentViewModel.setMutableLiveData_student_course_list(tlist);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }
    @Override
    public void onResume() {
//        mViewModel = new ViewModelProvider(this).get(TeacherViewModel.class);
        // TODO: Use the ViewModel
        super.onResume();
        studentFragmentBinding.studentFragmentTouchToEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransAnimationGo();
            }
        });
        studentFragmentBinding.studentFragmentTouchToResetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransAnimationBack();
            }
        });
        try {
            initStudentCourseList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        studentFragmentBinding = null;
    }
    public void TransAnimationGo(){
        Transition transition = TransitionsKt.fadeThrough().setDuration(MEDIUM_EXPAND_DURATION);
        TransitionManager.beginDelayedTransition(studentFragmentBinding.studentFragmentMoveConstrainParent,transition);
        studentFragmentBinding.studentFragmentResetUserPassword.setVisibility(View.VISIBLE);
        studentFragmentBinding.studentFragmentUserInformation.setVisibility(View.GONE);
    }
    public void TransAnimationBack(){
        Transition transition = TransitionsKt.fadeThrough().setDuration(MEDIUM_EXPAND_DURATION);
        TransitionManager.beginDelayedTransition(studentFragmentBinding.studentFragmentMoveConstrainParent,transition);
        studentFragmentBinding.studentFragmentResetUserPassword.setVisibility(View.GONE);
        studentFragmentBinding.studentFragmentUserInformation.setVisibility(View.VISIBLE);
    }

}