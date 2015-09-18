package com.example.nb_mis_100002.myapplication;

import cn.bmob.v3.BmobObject;

/**
 * Created by NB-MIS-100002 on 2015/9/18.
 */
public class FeedBack extends BmobObject {
    private String name,feedback;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
