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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shu.studentmanager.R;
import com.shu.studentmanager.StudentManagerApplication;
import com.shu.studentmanager.adpater.ScoreStudentAdapter;
import com.shu.studentmanager.databinding.ScoreReviewFragmentBinding;
import com.shu.studentmanager.entity.Course;
import com.shu.studentmanager.entity.ScoreTeacher;
import com.shu.studentmanager.viewmodel.ScoreReviewViewModel;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScoreReviewFragment extends Fragment {

    private ScoreReviewViewModel scoreReviewViewModel;
    private ScoreReviewFragmentBinding scoreReviewFragmentBinding;
    private AutoCompleteTextView scoreReviewTermSelect;
    private ArrayList<String> termList;
    private ArrayAdapter<String> termListAdapter;

    private RecyclerView score_review_list_recyclerview;
    private ScoreStudentAdapter scoreStudentAdapter;

    public static ScoreReviewFragment newInstance() {
        return new ScoreReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        scoreReviewViewModel = new ViewModelProvider(this).get(ScoreReviewViewModel.class);
        scoreReviewFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.score_review_fragment,container,false);
        scoreReviewFragmentBinding.setScoreReviewViewModel(scoreReviewViewModel);
        scoreReviewFragmentBinding.setLifecycleOwner(getActivity());
        setTermChooseDropdownMenu();

        score_review_list_recyclerview = scoreReviewFragmentBinding.scoreReviewFragmentRecyclerview;
        setScoreListRecyclerview();
        View root = scoreReviewFragmentBinding.getRoot();
        initScoreReviewList();
        return root;
//        return inflater.inflate(R.layout.score_review_fragment, container, false);
    }

    private void initScoreReviewList() {
        StudentManagerApplication application =(StudentManagerApplication) getActivity().getApplication();
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
                Response response;
                try {
                    response = client.newCall(request).execute();
                    if(response.isSuccessful()){
                        ArrayList<Course> slist = null;
                        slist = new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<Course>>(){}.getType());
                        scoreReviewViewModel.setMutableLiveData_score_review_score_list(slist);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void setScoreListRecyclerview() {
        score_review_list_recyclerview.setHasFixedSize(true);
        score_review_list_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        scoreStudentAdapter = new ScoreStudentAdapter(getActivity());
        score_review_list_recyclerview.setAdapter(scoreStudentAdapter);
        scoreReviewViewModel.getMutableLiveData_score_review_score_list().observe(getActivity(), new Observer<ArrayList<Course>>() {
            @Override
            public void onChanged(ArrayList<Course> courses) {
                scoreStudentAdapter.updateScoreStudentList(courses);
            }
        });
    }

    private void setTermChooseDropdownMenu() {
        scoreReviewTermSelect = scoreReviewFragmentBinding.scoreReviewTermSelect;
        termList = new ArrayList<String>();
        termList.add("22-春季学期");
        termListAdapter = new ArrayAdapter<>(getActivity(),R.layout.item_menu,termList);
        scoreReviewTermSelect.setAdapter(termListAdapter);
    }

    @Override
    public void onResume() {
//        mViewModel = new ViewModelProvider(this).get(TeacherViewModel.class);
        // TODO: Use the ViewModel
        super.onResume();
        initScoreReviewList();
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        scoreReviewFragmentBinding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        initScoreReviewList();
    }

}