package com.shu.studentmanager.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shu.studentmanager.entity.CourseTeacher;

import java.util.ArrayList;

public class ManageCourseViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<CourseTeacher>> mutableLiveData_course_list;

    public ManageCourseViewModel() {
    }

    public ManageCourseViewModel(MutableLiveData<ArrayList<CourseTeacher>> mutableLiveData_course_list) {
        this.mutableLiveData_course_list = mutableLiveData_course_list;
    }

    public MutableLiveData<ArrayList<CourseTeacher>> getMutableLiveData_course_list() {
        if(mutableLiveData_course_list == null){
            mutableLiveData_course_list = new MutableLiveData<>();
        }
        return mutableLiveData_course_list;
    }

    public void setMutableLiveData_course_list( ArrayList<CourseTeacher> mutableLiveData_course_list) {
        this.mutableLiveData_course_list.postValue((ArrayList<CourseTeacher>) mutableLiveData_course_list);
    }
}