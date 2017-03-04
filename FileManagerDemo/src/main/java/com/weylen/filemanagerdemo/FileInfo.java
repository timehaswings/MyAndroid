package com.weylen.filemanagerdemo;

/**
 * Created by zhou on 2016/5/3.
 */
public class FileInfo {

    public enum  FileType{
        DIR, AUDIO, VIDEO, TEXT, IMAGE, APK, UNKNOWN;
    }

    private String path;
    private String name;
    private FileType type;
    private String size;
    private int imgResId;

    public FileInfo() {
    }

    public FileInfo(String path, String name, FileType type, String size, int imgResId) {
        this.path = path;
        this.name = name;
        this.type = type;
        this.size = size;
        this.imgResId = imgResId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getImgResId() {
        return imgResId;
    }

    public void setImgResId(int imgResId) {
        this.imgResId = imgResId;
    }
}
