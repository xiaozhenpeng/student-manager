package com.shu.studentmanager.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shu.studentmanager.entity.Student;

import java.util.ArrayList;

public class ManageStudentViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<Student>> mutableLiveData_manage_student_list;

    public ManageStudentViewModel() {
    }

    public ManageStudentViewModel(MutableLiveData<ArrayList<Student>> mutableLiveData_manage_student_list) {
        this.mutableLiveData_manage_student_list = mutableLiveData_manage_student_list;
    }

    public MutableLiveData<ArrayList<Student>> getMutableLiveData_manage_student_list() {
        if(mutableLiveData_manage_student_list == null){
            mutableLiveData_manage_student_list = new MutableLiveData<ArrayList<Student>>();
        }
        return mutableLiveData_manage_student_list;
    }

    public void setMutableLiveData_manage_student_list(ArrayList<Student> mutableLiveData_manage_student_list) {
        this.mutableLiveData_manage_student_list.postValue((ArrayList<Student>)mutableLiveData_manage_student_list);
    }
}