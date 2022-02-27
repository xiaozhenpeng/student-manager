package com.shu.studentmanager.entity;

public class CourseTeacher {
    private String cid;
    private String cname;
    private String ccredit;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getCcredit() {
        return ccredit;
    }

    public void setCcredit(String ccredit) {
        this.ccredit = ccredit;
    }

    @Override
    public String toString() {
        return "CourseTeacher{" +
                "cid='" + cid + '\'' +
                ", cname='" + cname + '\'' +
                ", ccredit='" + ccredit + '\'' +
                '}';
    }
}
