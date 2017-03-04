package com.weylen.filemanagerdemo;

import java.io.File;
import java.text.DecimalFormat;

/**
 * Created by zhou on 2016/5/3.
 * 文件工具类
 */
public class FileUtil {

    /**
     * 获取文件类型
     * @param file
     * @return
     */
    public static FileInfo.FileType getFileType(File file){
        FileInfo.FileType fileType;
        if (file.isDirectory()){
            fileType = FileInfo.FileType.DIR;
        }else{
            String name = file.getName();
            if (name.endsWith(".apk")){
                fileType = FileInfo.FileType.APK;
            }else if (name.endsWith(".mp3") || name.endsWith(".amr") || name.endsWith(".flac")){
                fileType = FileInfo.FileType.AUDIO;
            }else if (name.endsWith(".mp4") || name.endsWith(".avi") || name.endsWith(".mkv")){
                fileType = FileInfo.FileType.VIDEO;
            }else if (name.endsWith(".jpg") || name.endsWith(".png") || name.endsWith(".bmp")){
                fileType = FileInfo.FileType.IMAGE;
            }else if (name.endsWith(".java") || name.endsWith(".txt") || name.endsWith(".doc")){
                fileType = FileInfo.FileType.TEXT;
            }else {
                fileType = FileInfo.FileType.UNKNOWN;
            }
        }
        return  fileType;
    }

    /**
     * 根据文件的类型得到文件显示的图标
     * @param fileType
     * @return
     */
    public static int getFileImgResId(FileInfo.FileType fileType){
        int imgResId;
        switch (fileType){
            case DIR:
                imgResId = R.mipmap.filelist_folder;
                break;
            case AUDIO:
                imgResId = R.mipmap.filelist_audio;
                break;
            case VIDEO:
                imgResId = R.mipmap.filelist_video;
                break;
            case APK:
                imgResId = R.mipmap.filelist_apk;
                break;
            case TEXT:
                imgResId = R.mipmap.filelist_document;
                break;
            case UNKNOWN:
                imgResId = R.mipmap.filelist_default;
                break;
            case IMAGE:
                imgResId = R.mipmap.filelist_image;
                break;
            default:
                imgResId = R.mipmap.filelist_default;
                break;
        }
        return imgResId;
    }

    /**
     * 根据文件对象得到文件的大小 单位是B K M G T
     * @param file
     * @return
     */
    public static String getFileSize(File file){
        if (file.isDirectory()){
            return "";
        }
        long length = file.length();
        String unit = "B";
        double size = length;
        if (length >= 1024){
            size = size / 1024;
            unit = "K";
            if (size >= 1024){
                size = size / 1024;
                unit = "M";
                if (size >= 1024){
                    size = size / 1024;
                    unit = "G";
                    if (size >= 1024){
                        size = size / 1024;
                        unit = "T";
                    }
                }
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String result = decimalFormat.format(size);
        return result + " " + unit;
    }
}
