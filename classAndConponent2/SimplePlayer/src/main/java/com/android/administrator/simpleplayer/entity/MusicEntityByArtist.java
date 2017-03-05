package com.android.administrator.simpleplayer.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/2/2.
 */
public class MusicEntityByArtist implements Serializable{

    private String artist;
    private int count;
    private List<MusicEntity> data;

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<MusicEntity> getData() {
        return data;
    }

    public void setData(List<MusicEntity> data) {
        this.data = data;
    }
}
