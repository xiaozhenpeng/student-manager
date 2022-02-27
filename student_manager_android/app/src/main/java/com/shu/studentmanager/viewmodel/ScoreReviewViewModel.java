package com.shu.studentmanager.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.shu.studentmanager.entity.Course;

import java.util.ArrayList;

public class ScoreReviewViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<ArrayList<String>> mutableLiveData_score_review_term_list;
    private MutableLiveData<ArrayList<Course>> mutableLiveData_score_review_score_list;

    public ScoreReviewViewModel() {
    }

    public ScoreReviewViewModel(MutableLiveData<ArrayList<String>> mutableLiveData_score_review_term_list) {
        this.mutableLiveData_score_review_term_list = mutableLiveData_score_review_term_list;
    }

    public ScoreReviewViewModel(MutableLiveData<ArrayList<String>> mutableLiveData_score_review_term_list, MutableLiveData<ArrayList<Course>> mutableLiveData_score_review_score_list) {
        this.mutableLiveData_score_review_term_list = mutableLiveData_score_review_term_list;
        this.mutableLiveData_score_review_score_list = mutableLiveData_score_review_score_list;
    }

    public MutableLiveData<ArrayList<String>> getMutableLiveData_score_review_term_list() {
        if(mutableLiveData_score_review_term_list == null){
            mutableLiveData_score_review_term_list = new MutableLiveData<ArrayList<String>>();
        }
        return mutableLiveData_score_review_term_list;
    }

    public void setMutableLiveData_score_review_term_list(ArrayList<String> mutableLiveData_score_review_term_list) {
        this.mutableLiveData_score_review_term_list.postValue((ArrayList<String>)mutableLiveData_score_review_term_list);
    }

    public MutableLiveData<ArrayList<Course>> getMutableLiveData_score_review_score_list() {
        if(mutableLiveData_score_review_score_list == null){
            mutableLiveData_score_review_score_list = new MutableLiveData<ArrayList<Course>>();
        }
        return mutableLiveData_score_review_score_list;
    }

    public void setMutableLiveData_score_review_score_list(ArrayList<Course> mutableLiveData_score_review_score_list) {
        this.mutableLiveData_score_review_score_list.postValue((ArrayList<Course>)mutableLiveData_score_review_score_list);
    }
}