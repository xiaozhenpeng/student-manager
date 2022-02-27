package com.shu.studentmanager.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shu.studentmanager.entity.Teacher;

import java.util.ArrayList;

public class ManageTeacherViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<Teacher>> mutableLiveData_manage_teacher_list;

    public ManageTeacherViewModel(MutableLiveData<ArrayList<Teacher>> mutableLiveData_manage_teacher_list) {
        this.mutableLiveData_manage_teacher_list = mutableLiveData_manage_teacher_list;
    }

    public ManageTeacherViewModel() {
    }

    public MutableLiveData<ArrayList<Teacher>> getMutableLiveData_manage_teacher_list() {
        if(mutableLiveData_manage_teacher_list == null){
            mutableLiveData_manage_teacher_list = new MutableLiveData<ArrayList<Teacher>>();
        }
        return mutableLiveData_manage_teacher_list;
    }

    public void setMutableLiveData_manage_teacher_list(ArrayList<Teacher> mutableLiveData_manage_teacher_list) {
        this.mutableLiveData_manage_teacher_list.postValue(mutableLiveData_manage_teacher_list);
    }
}