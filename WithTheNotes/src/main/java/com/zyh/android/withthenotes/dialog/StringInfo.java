package com.zyh.android.withthenotes.dialog;

import java.io.Serializable;

/**
 * Created by zhou on 2016/1/7.
 */
public class StringInfo implements Serializable{
    private String name;
    private boolean isChecked;

    // alt+insert

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public StringInfo(String name, boolean isChecked) {
        this.name = name;
        this.isChecked = isChecked;
    }

    public StringInfo() {
    }

    @Override
    public String toString() {
        return "StringInfo{" +
                "name='" + name + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }
}
