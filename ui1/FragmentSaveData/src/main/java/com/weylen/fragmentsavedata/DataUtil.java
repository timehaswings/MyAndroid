package com.weylen.fragmentsavedata;

/**
 * Created by zhou on 2016/4/6.
 */
public class DataUtil {

    private static DataUtil ourInstance = new DataUtil();

    public static DataUtil getInstance() {
//        if (ourInstance == null){
//            ourInstance = new DataUtil();
//        }
        return ourInstance;
    }

    private DataUtil() {
    }

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
