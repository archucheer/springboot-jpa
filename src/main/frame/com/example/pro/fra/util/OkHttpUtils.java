package com.example.pro.fra.util;

import com.example.pro.fra.ex.ResultCode;
import com.example.pro.fra.ex.ResultException;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

/**
 * @author Nebula
 * @version 1.0.0
 * @date 2018/7/7
 */
public class OkHttpUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(OkHttpUtils.class.getSimpleName());
    private static final OkHttpClient CLIENT = okHttpClient();

    public static ConnectionPool pool() {
        return new ConnectionPool(20, 5, TimeUnit.MINUTES);
    }

    public static OkHttpClient okHttpClientSSL() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectionPool(pool())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .sslSocketFactory(getSSLSocketFactory())//配置
                .hostnameVerifier(getHostnameVerifier())//配置
                .build();
    }

    public static OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectionPool(pool())
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    /**
     * get请求.
     *
     * @param url
     */
    public static String get(String url) {
        return originalGet(url, false,null);
    }
    public static String get(String url,OkHttpClient client) {
        return originalGet(url, false,client);
    }

    private static String originalGet(String url, boolean internalCalls,OkHttpClient client) {
        if(client==null){
            client=CLIENT;
        }
        LOGGER.info("request url: " + url);
        String responseBody = "";
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            int status = response.code();
            if (response.isSuccessful()) {
                responseBody = response.body().string();
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            if (!internalCalls) {
                throw new ResultException(ResultCode.ERROR_NET_RPC);
            }
        }
        LOGGER.info("response body: " + responseBody);
        if (responseBody == null && !internalCalls) {
            throw new ResultException(ResultCode.ERROR_NET_RPC);
        }
        return responseBody;
    }
    public static void downLoadGet(String url,String path) {
        downLoadGet(url, path,false,null);
    }
    public static void downLoadGet(String url,String path,OkHttpClient client) {
        downLoadGet(url, path,false,client);
    }

    private static void downLoadGet(String url,String path,boolean internalCalls,OkHttpClient client){
        if(client==null){
            client=CLIENT;
        }
        LOGGER.info("request url: " + url);
        String responseBody = "";
        Request request = new Request.Builder().url(url).build();
        try (Response response = client.newCall(request).execute()) {
            int status = response.code();
            if (response.isSuccessful()) {
                if("audio/amr".equals(response.body().contentType().toString())){
                    InputStream input=response.body().byteStream();
                    int index;
                    byte[] bytes = new byte[1024];
                    StringTokenizer st=new   StringTokenizer(path,File.separator);
                    String   path1=st.nextToken()+File.separator;
                    String   path2 =path1;
                    while(st.hasMoreTokens()){
                        path1=st.nextToken()+File.separator;
                        path2+=path1;
                        File inbox   =   new File(path2);
                        if(!inbox.exists())
                            inbox.mkdir();
                    }
                    path=path+File.separator+new Date().getTime();
                    FileOutputStream downloadFile = new FileOutputStream(path+"."+ ContentTypeUtil.getTypeByContentType("audio/amr"));
                    while ((index = input.read(bytes)) != -1) {
                        downloadFile.write(bytes, 0, index);
                        downloadFile.flush();
                    }
                    downloadFile.close();
                    input.close();
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            if (!internalCalls) {
                throw new ResultException(ResultCode.ERROR_NET_RPC);
            }
        }
        LOGGER.info("response body: " + responseBody);
        if (responseBody == null && !internalCalls) {
            throw new ResultException(ResultCode.ERROR_NET_RPC);
        }
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    public static String post(String url, String json) {
        return originalPost(url, json, false,null);
    }
    public static String post(String url, String json,OkHttpClient client) {
        return originalPost(url, json, false,client);
    }

    /**
     * post请求.
     */
    private static String originalPost(String url, String json, boolean internalCalls,OkHttpClient client) {
        if(client==null){
            client=CLIENT;
        }
        long beginTime = System.currentTimeMillis();
        String responseBody = null;
        boolean logFlag = !url.contains("192.168.138.123");
//        logFlag=true;
        LOGGER.info("request url: " + url);
        if (logFlag) {
            LOGGER.info("[ " + url + " ]request body: " + json);
        }
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        try (Response response = client.newCall(request).execute()) {
            int status = response.code();
            if (status != 200) {
                LOGGER.error("请求失败-状态码：" + status);
            }
            if (response.isSuccessful()) {
                responseBody = response.body().string();
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            if (!internalCalls) {
                throw new ResultException(ResultCode.ERROR_NET_RPC);
            }
        }
        if (logFlag) {
            LOGGER.info("[ " + url + " ]response body: " + responseBody);
        }
        LOGGER.info("请求花费时间: " + (System.currentTimeMillis() - beginTime) / 1000 + "s");
        if (responseBody == null && !internalCalls) {
            throw new ResultException(ResultCode.ERROR_NET_RPC);
        }
        return responseBody;
    }

    //获取这个SSLSocketFactory
    public static SSLSocketFactory getSSLSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, getTrustManager(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //获取TrustManager
    private static TrustManager[] getTrustManager() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        return trustAllCerts;
    }

    //获取HostnameVerifier
    public static HostnameVerifier getHostnameVerifier() {
        HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String s, SSLSession sslSession) {
                return true;
            }
        };
        return hostnameVerifier;
    }
    public static void main(String[] args) {
        get("http://api.nebulous.cn/v1/menu");
    }
}
