package com.shu.studentmanager.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shu.studentmanager.entity.CourseStudent;

import java.util.ArrayList;

public class CourseSelectViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<CourseStudent>> mutableLiveData_student_select_course_list;

    public CourseSelectViewModel() {
    }

    public CourseSelectViewModel(MutableLiveData<ArrayList<CourseStudent>> mutableLiveData_student_select_course_list) {
        this.mutableLiveData_student_select_course_list = mutableLiveData_student_select_course_list;
    }

    public MutableLiveData<ArrayList<CourseStudent>> getMutableLiveData_student_select_course_list() {
        if(mutableLiveData_student_select_course_list == null){
            mutableLiveData_student_select_course_list = new MutableLiveData<>();
        }
        return mutableLiveData_student_select_course_list;
    }

    public void setMutableLiveData_student_select_course_list(ArrayList<CourseStudent> mutableLiveData_student_select_course_list) {
        this.mutableLiveData_student_select_course_list.postValue((ArrayList<CourseStudent>) mutableLiveData_student_select_course_list);
    }
}