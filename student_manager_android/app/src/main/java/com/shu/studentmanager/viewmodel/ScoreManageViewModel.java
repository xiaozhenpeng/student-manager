package com.shu.studentmanager.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shu.studentmanager.entity.ScoreTeacher;

import java.util.ArrayList;

public class ScoreManageViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<String>> mutableLiveData_score_term_list;
    private MutableLiveData<ArrayList<ScoreTeacher>> mutableLiveData_score_list;
    public ScoreManageViewModel() {
    }

    public ScoreManageViewModel(MutableLiveData<ArrayList<String>> mutableLiveData_score_term_list) {
        this.mutableLiveData_score_term_list = mutableLiveData_score_term_list;
    }

    public ScoreManageViewModel(MutableLiveData<ArrayList<String>> mutableLiveData_score_term_list, MutableLiveData<ArrayList<ScoreTeacher>> mutableLiveData_score_list) {
        this.mutableLiveData_score_term_list = mutableLiveData_score_term_list;
        this.mutableLiveData_score_list = mutableLiveData_score_list;
    }


    public MutableLiveData<ArrayList<String>> getMutableLiveData_score_term_list() {
        if(mutableLiveData_score_term_list == null){
            mutableLiveData_score_term_list = new MutableLiveData<ArrayList<String>>();
        }
        return mutableLiveData_score_term_list;
    }

    public void setMutableLiveData_score_term_list(ArrayList<String> mutableLiveData_score_term_list) {
        this.mutableLiveData_score_term_list.postValue((ArrayList<String>) mutableLiveData_score_term_list);
    }

    public MutableLiveData<ArrayList<ScoreTeacher>> getMutableLiveData_score_list() {
        if(mutableLiveData_score_list == null){
            mutableLiveData_score_list = new MutableLiveData<ArrayList<ScoreTeacher>>();
        }
        return mutableLiveData_score_list;
    }

    public void setMutableLiveData_score_list(ArrayList<ScoreTeacher> mutableLiveData_score_list) {
        this.mutableLiveData_score_list.postValue((ArrayList<ScoreTeacher>)mutableLiveData_score_list);
    }

}