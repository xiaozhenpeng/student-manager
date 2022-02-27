package com.shu.studentmanager.entity;

public class CourseStudent {
    private String cid;
    private String tid;
    private String cname;
    private String tname;
    private String ccredit;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getCcredit() {
        return ccredit;
    }

    public void setCcredit(String ccredit) {
        this.ccredit = ccredit;
    }

    @Override
    public String toString() {
        return "CourseStudent{" +
                "cid='" + cid + '\'' +
                ", tid='" + tid + '\'' +
                ", cname='" + cname + '\'' +
                ", tname='" + tname + '\'' +
                ", ccredit='" + ccredit + '\'' +
                '}';
    }
}
