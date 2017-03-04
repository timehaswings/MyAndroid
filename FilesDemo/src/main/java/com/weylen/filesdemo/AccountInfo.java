package com.weylen.filesdemo;

import java.io.Serializable;

/**
 * Created by zhou on 2016/4/13.
 */
public class AccountInfo implements Serializable{

    private String name, pwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
