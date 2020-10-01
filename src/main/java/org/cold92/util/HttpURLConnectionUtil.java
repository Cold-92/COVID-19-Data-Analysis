package org.cold92.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnectionUtil {

    /**
     * 从程序中发起Http请求,获取实时数据
     * @param urlStr Http请求的url
     * @return
     */
    public static String doGet(String urlStr) {
        // java中发送的Http请求连接
        HttpURLConnection connection = null;
        // 获取响应信息流
        InputStream inputStream = null;
        BufferedReader reader = null;
        // 接收到的响应数据
        StringBuffer dataStr = new StringBuffer();

        try {
            // 构建URL
            URL url = new URL(urlStr);
            // 构建连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接参数
            connection.setRequestMethod("GET");
            // 设置请求连接保持的时间
            connection.setConnectTimeout(60000);
            // 设置在连接过程中读取数据保持的时间
            connection.setReadTimeout(15000);
            // 设置客户端期望接收的数据类型
            connection.setRequestProperty("Accept", "application/json");
            // 发送请求
            connection.connect();
            // 判断连接是否成功
            if (connection.getResponseCode() != 200) {
                // TODO 抛除异常
                return "error code";
            }
            // 建立数据输入流，并包装成字符流
            inputStream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            // 接收数据
            String line = null;
            while ((line = reader.readLine()) != null) {
                dataStr.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataStr.toString();
    }
}
