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
import com.shu.studentmanager.databinding.AdminFragmentBinding;
import com.shu.studentmanager.entity.CourseStudent;
import com.shu.studentmanager.transition.TransitionsKt;
import com.shu.studentmanager.viewmodel.AdminViewModel;
import com.shu.studentmanager.viewmodel.StudentViewModel;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AdminFragment extends Fragment {

    private AdminViewModel adminViewModel;
    private AdminFragmentBinding adminFragmentBinding;

    public static AdminFragment newInstance() {
        return new AdminFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        adminViewModel = new ViewModelProvider(this).get(AdminViewModel.class);
        adminFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.admin_fragment,container,false);
        adminFragmentBinding.setAdminViewModel(adminViewModel);
        adminFragmentBinding.setLifecycleOwner(getActivity());

        StudentManagerApplication application = (StudentManagerApplication) getActivity().getApplication();
        adminFragmentBinding.adminFramentTeacherId.setText(application.getId());
        adminFragmentBinding.adminFragmentTeacherName.setText(application.getName());

        View root = adminFragmentBinding.getRoot();
        return root;
//        return inflater.inflate(R.layout.student_fragment, container, false);
    }

    @Override
    public void onResume() {
//        mViewModel = new ViewModelProvider(this).get(TeacherViewModel.class);
        // TODO: Use the ViewModel
        super.onResume();
        adminFragmentBinding.adminTouchToEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransAnimationGo();
            }
        });
        adminFragmentBinding.adminTouchToResetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransAnimationBack();
            }
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adminFragmentBinding = null;
    }
    public void TransAnimationGo(){
        Transition transition = TransitionsKt.fadeThrough().setDuration(MEDIUM_EXPAND_DURATION);
        TransitionManager.beginDelayedTransition(adminFragmentBinding.adminMoveConstrainParent,transition);
        adminFragmentBinding.adminResetUserPassword.setVisibility(View.VISIBLE);
        adminFragmentBinding.adminUserInformation.setVisibility(View.GONE);
    }
    public void TransAnimationBack(){
        Transition transition = TransitionsKt.fadeThrough().setDuration(MEDIUM_EXPAND_DURATION);
        TransitionManager.beginDelayedTransition(adminFragmentBinding.adminMoveConstrainParent,transition);
        adminFragmentBinding.adminResetUserPassword.setVisibility(View.GONE);
        adminFragmentBinding.adminUserInformation.setVisibility(View.VISIBLE);
    }

}