package com.weylen.asynctaskdemo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by zhou on 2016/4/29.
 */
public class DownloadUtil {

    /**
     *
     * @return 返回Url地址对应的流
     */
    public static InputStream download(String url) throws IOException {

        return new URL(url).openConnection().getInputStream();
    }
}
