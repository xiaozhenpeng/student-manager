package com.shu.studentmanager.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shu.studentmanager.R;
import com.shu.studentmanager.StudentManagerApplication;
import com.shu.studentmanager.adpater.ScoreTeacherAdapter;
import com.shu.studentmanager.databinding.ScoreManageFragmentBinding;
import com.shu.studentmanager.entity.ScoreTeacher;
import com.shu.studentmanager.viewmodel.ScoreManageViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ScoreManageFragment extends Fragment {

    private ScoreManageViewModel scoreManageViewModel;
    private ScoreManageFragmentBinding scoreManageFragmentBinding;
    private AutoCompleteTextView scoreManageTermSelect;
    private ArrayList<String> termList;
    private ArrayAdapter<String> termListAdapter;

    private RecyclerView score_list_recyclerview;
    private ScoreTeacherAdapter scoreTeacherAdapter;

    public static ScoreManageFragment newInstance() {
        return new ScoreManageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        scoreManageViewModel = new ViewModelProvider(this).get(ScoreManageViewModel.class);
        scoreManageFragmentBinding = DataBindingUtil.inflate(inflater,R.layout.score_manage_fragment,container,false);
        scoreManageFragmentBinding.setScoreManageViewModel(scoreManageViewModel);
        scoreManageFragmentBinding.setLifecycleOwner(getActivity());
        setTermChooseDropdownMenu();
        setEditTextChangedListener();

        score_list_recyclerview = scoreManageFragmentBinding.scoreManageFragmentRecyclerview;
        setScoreListRecyclerview();
        View root = scoreManageFragmentBinding.getRoot();
        try {
            initScoreList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return root;
//        return inflater.inflate(R.layout.score_manage_fragment, container, false);
    }

    private void setScoreListRecyclerview() {
        score_list_recyclerview.setHasFixedSize(true);
        score_list_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        scoreTeacherAdapter = new ScoreTeacherAdapter(getActivity());
        score_list_recyclerview.setAdapter(scoreTeacherAdapter);
        scoreManageViewModel.getMutableLiveData_score_list().observe(getActivity(), new Observer<ArrayList<ScoreTeacher>>() {
            @Override
            public void onChanged(ArrayList<ScoreTeacher> scoreTeachers) {
                scoreTeacherAdapter.updateScoreTeacherList(scoreTeachers);
            }
        });
    }
    private void setEditTextChangedListener() {
        scoreManageFragmentBinding.scoreManageTermSelect.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    initScoreList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        scoreManageFragmentBinding.scoreManageFragmentStudentNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    initScoreList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        scoreManageFragmentBinding.scoreManageFragmentStudentName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    initScoreList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        scoreManageFragmentBinding.scoreManageFragmentCourseName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    initScoreList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        scoreManageFragmentBinding.scoreManageFragmentCourseNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    initScoreList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        scoreManageFragmentBinding.scoreManageFragmentHigh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    initScoreList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        scoreManageFragmentBinding.scoreManageFragmentLow.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    initScoreList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        scoreManageFragmentBinding.scoreManageFragmentFuzzySearch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                try {
                    initScoreList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setTermChooseDropdownMenu() {
        scoreManageTermSelect = scoreManageFragmentBinding.scoreManageTermSelect;
        termList = new ArrayList<String>();
        termList.add("22-春季学期");
        termListAdapter = new ArrayAdapter<>(getActivity(),R.layout.item_menu,termList);
        scoreManageTermSelect.setAdapter(termListAdapter);


    }

    private void initScoreList() throws JSONException {
        MediaType JSON = MediaType.parse("application/json;charset=utf-8");
        StudentManagerApplication application =(StudentManagerApplication) getActivity().getApplication();
        JSONObject json = new JSONObject();
        json.put("cid",scoreManageFragmentBinding.scoreManageFragmentCourseNumber.getText().toString());
        json.put("cname",scoreManageFragmentBinding.scoreManageFragmentCourseName.getText().toString());
        json.put("cFuzzy",scoreManageFragmentBinding.scoreManageFragmentFuzzySearch.isChecked());
        json.put("highBound",scoreManageFragmentBinding.scoreManageFragmentHigh.getText().toString());
        json.put("lowBound",scoreManageFragmentBinding.scoreManageFragmentLow.getText().toString());
        json.put("sid",scoreManageFragmentBinding.scoreManageFragmentStudentNumber.getText().toString());
        json.put("sname",scoreManageFragmentBinding.scoreManageFragmentStudentName.getText().toString());
        json.put("sFuzzy",scoreManageFragmentBinding.scoreManageFragmentFuzzySearch.isChecked());
        json.put("term",scoreManageFragmentBinding.scoreManageTermSelect.getText().toString());
        if(application.getName().equals("admin")){
            json.put("tid","");
            json.put("tname","");
        } else{
            json.put("tid",application.getId());
            json.put("tname",application.getName());
        }
        json.put("tFuzzy",scoreManageFragmentBinding.scoreManageFragmentFuzzySearch.isChecked());
//        Log.d(TAG, "initCourseList: "+ json.toString() );
        new Thread(){
            @Override
            public void run(){
                super.run();
                OkHttpClient client = new OkHttpClient().newBuilder()
                        .build();
                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(JSON,json.toString());
                Request request = new Request.Builder()
                        .url("http://101.35.20.64:10086/SCT/findBySearch")
                        .method("POST", body)
                        .addHeader("Content-Type", "application/json")
                        .build();
                Response response;
                try {
                    response = client.newCall(request).execute();
                    if(response.isSuccessful()){
                        ArrayList<ScoreTeacher> slist = null;
                        slist = new Gson().fromJson(response.body().string(), new TypeToken<ArrayList<ScoreTeacher>>(){}.getType());
                        scoreManageViewModel.setMutableLiveData_score_list(slist);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }.start();

    }    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        scoreManageViewModel = new ViewModelProvider(this).get(ScoreManageViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onResume() {
//        mViewModel = new ViewModelProvider(this).get(TeacherViewModel.class);
        // TODO: Use the ViewModel
        super.onResume();
        try {
            initScoreList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        scoreManageFragmentBinding = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            initScoreList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}