package org.cold92.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClientUtil {

    /**
     * 手动搭建封装后的Http请求，效率比HttpURLConnectionUtl发送的http要高
     * @param urlStr Http请求的url
     * @return
     */
    public static String doGet(String urlStr) {
        // 封装后的发送http请求的客户端
        CloseableHttpClient httpClient = null;
        // http请求获取的响应
        CloseableHttpResponse httpResponse = null;
        // 响应的数据
        String dataStr = null;
        try {
            // 创建http客户端
            httpClient = HttpClients.createDefault();
            // 创建具体封装后的请求
            HttpGet httpGet = new HttpGet(urlStr);
            // 设置客户端期望接收的数据类型
            httpGet.setHeader("Accept", "application/json");
            // 设置请求的参数
            RequestConfig requestConfig = RequestConfig.custom()
                    // 设置请求连接保持的时间
                    .setConnectTimeout(60000)
                    // 设置从共享池中取出连接的超时时间
                    .setConnectionRequestTimeout(15000)
                    // 设置在连接过程中读取数据保持的时间
                    .setSocketTimeout(15000)
                    .build();
            // 加载配置参数
            httpGet.setConfig(requestConfig);
            // 发送请求
            httpResponse = httpClient.execute(httpGet);
            // 获取响应数据
            HttpEntity httpEntity = httpResponse.getEntity();
            // 将响应数据转换成字符串
            dataStr = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataStr;
    }
}
