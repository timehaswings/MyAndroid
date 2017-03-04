package com.android.administrator.simpleplayer.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/29.
 */
public class MusicEntity implements Serializable{

    private String name, artist, album, path, duration, id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MusicEntity(String name, String artist, String album, String path, String duration, String id) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.path = path;
        this.duration = duration;
        this.id = id;
    }

    public MusicEntity() {
    }
}
