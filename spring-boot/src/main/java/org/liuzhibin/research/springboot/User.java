package org.liuzhibin.research.springboot;

import java.util.Date;

public class User {
    private int id;
    private String name;
    private Date createTime;

    public int getId() {
        return id;
    }

    public void setId(int value) {
        this.id = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date value) {
        this.createTime = value;
    }
}