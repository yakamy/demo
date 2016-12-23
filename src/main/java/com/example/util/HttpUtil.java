package com.example.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/12/23.
 */
public class HttpUtil {

    private static CloseableHttpClient httpClient = HttpClients.createDefault();
    private static CloseableHttpResponse response;
    public static InputStream post(String url, String data) {

        RequestConfig config = RequestConfig.custom().setConnectTimeout(6000).setConnectionRequestTimeout(6000).setSocketTimeout(6000).build();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(config);
        try {
            httpPost.setEntity(new StringEntity(data, ContentType.APPLICATION_JSON));
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = entity.getContent();
                return inputStream;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void releaseResource(){
        if (response != null) {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (httpClient != null) {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
