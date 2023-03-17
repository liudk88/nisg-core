package com.hcxinan.core.net;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    private static final Logger log = Logger.getLogger(HttpUtil.class);

    private static Map<String,HttpUtilRegister> registerMap=new HashMap<String,HttpUtilRegister>();//注册信息

    private static SSLSocketFactory ssf=null;//使用https时需要设置的一些安全信息
    static {
        try{
            //创建SSLContext
            SSLContext sslContext=SSLContext.getInstance("SSL");
            X509TrustManager xtm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            TrustManager[] tm={xtm};
            //初始化
            sslContext.init(null, tm, new java.security.SecureRandom());;
            //获取SSLSocketFactory对象
            ssf=sslContext.getSocketFactory();

            if (sslContext != null) {
                HttpsURLConnection.setDefaultSSLSocketFactory(sslContext.getSocketFactory());
            }
            HostnameVerifier hnv = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(hnv);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
    *@Description：：:绑定注册者的类名和其注册信息绑定注册者的类名和其注册信息绑定注册者的类名和其注册信息绑定 绑定注册者的类名和其注册信息。注册的目的是为了设置一些参数，在后续的一些方法里面可以方便取出
    *@Param [hr]:注册信息
    *@Return void
    *@Author liudk
    *@Date 19-9-2
    *@Time 下午5:05
    */
    public static void registerTools(HttpUtilRegister hr,Class c){
        registerMap.put(c.getName(),hr);//绑定注册者的类名和其注册信息
    }

    /**
    *@Description 发送get请求
    *@Param [urlStr:请求地址, params:请求参数，toolClass注册的工具类]
    *@Return java.lang.String
    *@Author liudk
    *@Date 19-9-2
    *@Time 下午2:54
    */
    public static String sendGet(String urlStr,String params,Class toolClass) throws IOException {
        log.info("请求参数："+params);
        if (params != null && !params.equals("")) {
            urlStr += "?" + params;
            urlStr.replaceAll(" ", "");
        }
        return send(urlStr,null,"GET",toolClass);
    }
    /**
    *@Description 发送post请求
    *@Param [urlStr:请求地址, params:请求参数，toolClass注册的工具类]
    *@Return java.lang.String
    *@Author liudk
    *@Date 19-9-2
    *@Time 下午5:11
    */
    public static String sendPost(String urlStr,String params,Class toolClass) throws IOException {
        log.info("请求参数："+params);
        return send(urlStr,params,"POST",toolClass);
    }
    /**
    *@Description 发送post请求
    *@Param [urlStr:请求地址, params:请求参数，toolClass注册的工具类]
    *@Return java.lang.String
    *@Author liudk
    *@DateTime 19-12-20 下午4:38
    */
    public static String sendPost(String urlStr,Map params,Class toolClass) throws IOException {
        /*log.info("请求参数："+params);*/
        return sendPost(urlStr,builderUrlParams(params),toolClass);
    }
    /**
    *@Description 发送post请求
    *@Param [urlStr:请求地址, params:请求参数，toolClass注册的工具类, filepaths,需要上传的文件]
    *@Return java.lang.String
    *@Author liudk
    *@DateTime 19-12-23 下午3:18
    */
    public static String sendPost(String urlStr,final Map<String,String> params,final Class toolClass, List<File> files){
        HttpUtilRegister hr=null;//当前工具类的注册信息
        if(toolClass!=null){
            hr=registerMap.get(toolClass.getName());
        }
        if(hr!=null && hr.getDomain()!=null && urlStr.indexOf(hr.getDomain())==-1){//有注册信息,有域名，把域名自动补上
            urlStr=hr.getDomain()+urlStr;
        }
        HttpClient client = new DefaultHttpClient();
        if(hr.isOpenSSL()){//使用了ssl/https
            try {
                SSLContext sslContext = new SSLContextBuilder()
                        .loadTrustMaterial(null, (certificate, authType) -> true).build();
                client = HttpClients.custom()
                        .setSSLContext(sslContext)
                        .setSSLHostnameVerifier(new NoopHostnameVerifier())
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        HttpPost httpPost = new HttpPost(urlStr);
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        if(files!=null && files.size()>0){//存在上传文件的情况
            builder.setMode(HttpMultipartMode.RFC6532);//防止附件名称乱码
            for(File file:files){
                builder.addPart("doc", new FileBody(file));
            }
        }
        try{
            for(Map.Entry<String,String> entry:params.entrySet()){
                builder.addPart(entry.getKey(), new StringBody(entry.getValue(),Charset.forName("utf-8")));  // 字符参数部分，采用utf-8编码
            }
            HttpEntity entity = builder.build();// 生成 HTTP POST 实体
            httpPost.setEntity(entity);//设置请求参数
            HttpResponse response = client.execute(httpPost);// 发起请求 并返回请求的响应

            if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK) {
                return EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
    *@Description 发送请求
    *@Param [urlStr:请求地址, params:请求参数, methodType:请求方式]
    *@Return java.lang.String
    *@Author liudk
    *@Date 19-9-2
    *@Time 下午5:55
    */
    private static String send(String urlStr,String params,String methodType,Class toolClass) throws IOException {
        StringBuffer buffer=null;
        HttpURLConnection conn=getHttpURLConnection(urlStr,methodType,toolClass);
        //往服务器端写内容 也就是发起http请求需要带的参数
        if(null!=params){
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            writer.write(params);
            writer.close();
        }
        //读取服务器端返回的内容
        InputStream is=conn.getInputStream();
        InputStreamReader isr=new InputStreamReader(is,"utf-8");
        BufferedReader br=new BufferedReader(isr);
        buffer=new StringBuffer();
        String line=null;
        while((line=br.readLine())!=null){
            buffer.append(line);
        }
        return buffer.toString();
    }
    /**
    *@Description 获取连接
    *@Param [urlStr, methdType]
    *@Return java.net.HttpURLConnection
    *@Author liudk
    *@Date 19-9-2
    *@Time 下午5:24
    */
    public static HttpURLConnection getHttpURLConnection(String urlStr,String methdType,Class toolClass) throws IOException {
        HttpUtilRegister hr=null;//当前工具类的注册信息
        if(toolClass!=null){
            hr=registerMap.get(toolClass.getName());
        }
        if(hr!=null && hr.getDomain()!=null && urlStr.indexOf(hr.getDomain())==-1){//有注册信息,有域名，把域名自动补上
            urlStr=hr.getDomain()+urlStr;
        }
        URL url=new URL(urlStr);
        HttpURLConnection conn=(HttpURLConnection)url.openConnection();
        if(hr!=null && hr.isOpenSSL()){//有注册信息，并打开了安全认证，使用的是https
            //设置当前实例使用的SSLSoctetFactory
            HttpsURLConnection conn2= (HttpsURLConnection) conn;
            conn2.setSSLSocketFactory(ssf);
            conn2.setUseCaches(false);
            log.info("发送https请求："+urlStr);
        }else {
            log.info("发送http请求："+urlStr);
        }
        conn.setInstanceFollowRedirects(false);
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod(methdType);
        conn.setConnectTimeout(30000);
        conn.connect();
        return conn;
    }

    private static String builderUrlParams(Map<String,Object> params){
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<String,Object> entry:params.entrySet()){
            String value = entry.getValue()+"";
            String key=entry.getKey();
            sb.append(key);
            sb.append("=");
            sb.append(value);
            sb.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

}
