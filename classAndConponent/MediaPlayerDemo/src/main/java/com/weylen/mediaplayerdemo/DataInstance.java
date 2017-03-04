package com.weylen.mediaplayerdemo;

import java.util.List;

/**
 * Created by zhou on 2016/4/22.
 */
public class DataInstance {

    private static DataInstance ourInstance = new DataInstance();

    public static DataInstance getInstance() {
        return ourInstance;
    }

    private DataInstance() {
    }

    private List<MediaBean> data;

    public List<MediaBean> getData() {
        return data;
    }

    public void setData(List<MediaBean> data) {
        this.data = data;
    }
}
