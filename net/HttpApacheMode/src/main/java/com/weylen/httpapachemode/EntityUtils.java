package com.weylen.httpapachemode;

import org.apache.http.HttpEntity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by zhou on 2016/4/27.
 */
public class EntityUtils {

    public static String toString(HttpEntity entity) throws IOException {
        if (entity == null){return null;}
        InputStream inputStream = entity.getContent();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line ;
        String result = "";
        while((line = bufferedReader.readLine()) != null){
            result += line + "\n";
        }
        bufferedReader.close();
        inputStream.close();
        return result;
    }

    public static String toString(HttpEntity entity, String charsetName) throws IOException {
        if (entity == null){return null;}
        InputStream inputStream = entity.getContent();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charsetName));
        String line ;
        String result = "";
        while((line = bufferedReader.readLine()) != null){
            result += line + "\n";
        }
        bufferedReader.close();
        inputStream.close();
        return result;
    }
}
