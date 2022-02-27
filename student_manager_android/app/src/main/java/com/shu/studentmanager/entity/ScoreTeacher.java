package com.shu.studentmanager.entity;

import java.security.PrivateKey;

public class ScoreTeacher {
//    "sid": 2,
//            "tid": 4,
//            "cid": 7,
//            "sname": "张四",
//            "tname": "李玉民",
//            "cname": "数据结构",
//            "grade": 12.0,
//            "term": "22-春季学期"
    private String sid;
    private String tid;
    private String cid;
    private String sname;
    private String tname;
    private String cname;
    private String grade;
    private String term;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    @Override
    public String toString() {
        return "ScoreTeacher{" +
                "sid='" + sid + '\'' +
                ", tid='" + tid + '\'' +
                ", cid='" + cid + '\'' +
                ", sname='" + sname + '\'' +
                ", tname='" + tname + '\'' +
                ", cname='" + cname + '\'' +
                ", grade='" + grade + '\'' +
                ", term='" + term + '\'' +
                '}';
    }
}
