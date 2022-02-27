package com.shu.studentmanager.fragment;

import static android.content.ContentValues.TAG;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shu.studentmanager.R;
import com.shu.studentmanager.StudentManagerApplication;
import com.shu.studentmanager.adpater.CourseAdapter;
import com.shu.studentmanager.databinding.TeacherFragmentBinding;
import com.shu.studentmanager.entity.Course;
import com.shu.studentmanager.entity.CourseTeacher;
import com.shu.studentmanager.viewmodel.TeacherViewModel;
import com.shu.studentmanager.transition.TransitionsKt;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TeacherFragment extends Fragment {

    private TeacherViewModel teacherViewModel;
    public static TeacherFragmentBinding teacherFragmentBinding;
    public static TeacherFragment newInstance() {
        return new TeacherFragment();
    }

    private RecyclerView course_list_recycleview;
    private CourseAdapter courseAdapter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        teacherViewModel = new ViewModelProvider(this).get(TeacherViewModel.class);
        teacherFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.teacher_fragment,container,false);
        teacherFragmentBinding.setTeacherViewModel(teacherViewModel);
        teacherFragmentBinding.setLifecycleOwner(getActivity());

        StudentManagerApplication application =(StudentManagerApplication) getActivity().getApplication();
        teacherFragmentBinding.teacherFragmentTeacherName.setText(application.getName());
        teacherFragmentBinding.teacherFramentTeacherId.setText(application.getId());

        course_list_recycleview = teacherFragmentBinding.courseListRecycleview;
        setCoureListRecycleView();
        View root = teacherFragmentBinding.getRoot();
        try {
            initCourseList();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
//        return inflater.inflate(R.layout.teacher_fragment, container, false);
    }
    private void setCoureListRecycleView() {
        course_list_recycleview.setHasFixedSize(true);
        course_list_recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
        courseAdapter = new CourseAdapter(getActivity());
        course_list_recycleview.setAdapter(courseAdapter);
        teacherViewModel.getMutableLiveData_course_list().observe(getActivity(), new Observer<ArrayList<CourseTeacher>>() {
            @Override
            public void onChanged(ArrayList<CourseTeacher> courseTeachers) {
//                Log.d(TAG, "onChanged: update");
                teacherFragmentBinding.teacherFragmentTeacherClassNumber.setText("本学期开设 "+String.valueOf(courseTeachers.size())+" 门课");
                courseAdapter.updateCourseList(courseTeachers);
            }
        });
    }

    private void initCourseList() throws IOException {
        StudentManagerApplication application =(StudentManagerApplication) getActivity().getApplication();
        String url = "http://101.35.20.64:10086/courseTeacher/findMyCourse/"+application.getId()+"/"+application.getCurrentTerm();
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
                        ArrayList<CourseTeacher> tlist = new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<CourseTeacher>>() {}.getType());
//                        mlist.addAll(tlist);
                        teacherViewModel.setMutableLiveData_course_list(tlist);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    /**
     * 应该放在viewModel中
     * @return
     */
//    private List<Course> getCourseList() {
//        List<Course> mlist = new ArrayList<>();
//        String url = "http://101.35.20.64:10086/SCT/findBySid/2/22-春季学期";
//        new Thread(){
//            @Override
//            public void run(){
//                super.run();
//                OkHttpClient client = new OkHttpClient().newBuilder()
//                        .build();
//                Request request = new Request.Builder()
//                        .url(url)
//                        .method("GET", null)
//                        .build();
//                try {
//                    Response response = client.newCall(request).execute();
//                    if(response.isSuccessful()){
//                        List<Course> tlist = new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<Course>>() {}.getType());
//                        mlist.addAll(tlist);
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();
//        return mlist;
//    }

    @Override
    public void onResume() {
//        mViewModel = new ViewModelProvider(this).get(TeacherViewModel.class);
        // TODO: Use the ViewModel
        super.onResume();
        teacherFragmentBinding.touchToEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransAnimationGo();
            }
        });
        teacherFragmentBinding.touchToResetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransAnimationBack();
            }
        });
        try {
            initCourseList();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        teacherFragmentBinding = null;
    }

    /**
     * 过渡动画进入
     *
     */
    public void TransAnimationGo() {

        Transition transition = TransitionsKt.fadeThrough().setDuration(MEDIUM_EXPAND_DURATION);
        TransitionManager.beginDelayedTransition(teacherFragmentBinding.moveConstrainParent,transition);
        teacherFragmentBinding.resetUserPassword.setVisibility(View.VISIBLE);
        teacherFragmentBinding.userInformation.setVisibility(View.GONE);

//        Delays the fade-through transition until the layout change below takes effect.
//        ObjectAnimator animation = ObjectAnimator.ofFloat(teacherFragmentBinding.userInformation, "translationY", 700f);
//        animation.setDuration(300);
//        animation.start();
//        ObjectAnimator animation2 = ObjectAnimator.ofFloat(teacherFragmentBinding.resetUserPassword,"translationY",700f);
//        animation2.setDuration(300);
//        animation2.start();
    }
    /**
     * 过渡动画返回
     *
     */
    public void TransAnimationBack() {
        Transition transition = TransitionsKt.fadeThrough().setDuration(MEDIUM_EXPAND_DURATION);
        TransitionManager.beginDelayedTransition(teacherFragmentBinding.moveConstrainParent,transition);
        teacherFragmentBinding.resetUserPassword.setVisibility(View.GONE);
        teacherFragmentBinding.userInformation.setVisibility(View.VISIBLE);
    }
}