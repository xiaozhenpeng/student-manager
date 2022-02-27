package com.shu.studentmanager;

import android.app.Application;

public class StudentManagerApplication extends Application {
    private Boolean token;
    private String type;
    private String name;
    private String id;
    private String currentTerm;
    private Boolean forbidCourseSelection;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    public Boolean getToken() {
        return token;
    }

    public void setToken(Boolean token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrentTerm() {
        return currentTerm;
    }

    public void setCurrentTerm(String currentTerm) {
        this.currentTerm = currentTerm;
    }

    public Boolean getForbidCourseSelection() {
        return forbidCourseSelection;
    }

    public void setForbidCourseSelection(Boolean forbidCourseSelection) {
        this.forbidCourseSelection = forbidCourseSelection;
    }
}
