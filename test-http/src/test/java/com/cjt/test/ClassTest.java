package com.cjt.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjt.test.bean.MessageApiReturnData;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @Author: chenjintao
 * @Date: 2019-11-29 14:39
 */
@RunWith(JUnit4.class)
public class ClassTest {


    @Test
    public void test1() {
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(10 * 1000);
        httpRequestFactory.setReadTimeout(60 * 1000);

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        String url = "http://10.1.200.155:8101/list/test?batchId=31d26812dc8f40f88d4c0264750ce48b&isEncrypt=true";
        HttpHeaders httpHeaders = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        ResponseEntity<List> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, List.class);

        List<String> numberList = responseEntity.getBody();

        System.out.println(numberList.toString());
    }


    @Test
    public void testMessage1() {
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(10 * 1000);
        httpRequestFactory.setReadTimeout(60 * 1000);

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        HttpHeaders httpHeaders = new HttpHeaders();
        String param = "{\n" +
                "\t\"params\": {\n" +
                "\t\t\"rspId\": \"5198\",\n" +
                "\t\t\"billId\": \"15938727890\",\n" +
                "\t\t\"userName\": \"zxzndtsub\",\n" +
                "\t\t\"apiKey\": \"6fe47cdf4fcc9ed803fb55a249205b23\",\n" +
                "\t\t\"infoMap\": {\n" +
                "\t\t\t\"message_data\":\"123456\"\n" +
                "\t\t}\n" +
                "\t}\n" +
                "}";
        JSONObject paramJson = JSON.parseObject(param);
        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(paramJson, httpHeaders);
        String url = "http://127.0.0.1:9000/test/message/api";
        ResponseEntity<MessageApiReturnData> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, MessageApiReturnData.class);
        System.out.println(responseEntity.getBody());

    }


    @Test
    public void testMessage2() {
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(10 * 1000);
        httpRequestFactory.setReadTimeout(60 * 1000);

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        HttpHeaders httpHeaders = new HttpHeaders();
        String param = "{\n" +
                "\t\"params\": {\n" +
                "\t\t\"rspId\": \"5198\",\n" +
                "\t\t\"billId\": \"15938727890\",\n" +
                "\t\t\"userName\": \"zxzndtsub\",\n" +
                "\t\t\"apiKey\": \"6fe47cdf4fcc9ed803fb55a249205b23\"\n" +
                "\t}\n" +
                "}";
        JSONObject paramJson = JSON.parseObject(param);
        Map<String, Object> infoMap = new HashMap<>();
        paramJson.getJSONObject("params").put("infoMap", infoMap);
        infoMap.put("message_code", "123456");

        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(paramJson, httpHeaders);
        String url = "http://127.0.0.1:9000/test/message/api";
        ResponseEntity<MessageApiReturnData> responseEntity = restTemplate.exchange(url, HttpMethod.POST, httpEntity, MessageApiReturnData.class);
        System.out.println(responseEntity.getBody());

    }


    @Test
    public void testHttps1() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(100);
        connectionManager.setValidateAfterInactivity(2000);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(65000) // 服务器返回数据(response)的时间，超时抛出read timeout
                .setConnectTimeout(5000) // 连接上服务器(握手成功)的时间，超时抛出connect timeout
                .setConnectionRequestTimeout(1000)// 从连接池中获取连接的超时时间，超时抛出ConnectionPoolTimeoutException
                .build();
        HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setConnectionManager(connectionManager).build();

        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
//        //Add the Jackson Message converter
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        // Note: here we are making this converter to process any kind of response,
//        // not only application/*json, which is the default behaviour
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//        messageConverters.add(converter);


        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
      //  restTemplate.setMessageConverters(messageConverters);
        HttpHeaders httpHeaders = new HttpHeaders();


        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(httpHeaders);
        String url = "https://www.baifubao.com/callback?cmd=1059&callback=phone&phone=15515091290";
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        System.out.println(responseEntity.getBody());

    }

    @Test
    public void testHttps2(){
        SimpleClientHttpRequestFactory httpRequestFactory = new SimpleClientHttpRequestFactory();
        httpRequestFactory.setConnectTimeout(10 * 1000);
        httpRequestFactory.setReadTimeout(60 * 1000);

        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        HttpHeaders httpHeaders = new HttpHeaders();


        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(httpHeaders);
        String url = "https://www.baifubao.com/callback?cmd=1059&callback=phone&phone=15515091290";
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        System.out.println(responseEntity.getBody());

    }

    @Test
    public void testIgnoreCheckKey1() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) {

            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) {

            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }
        }}, null);

        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE))
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(100);
        connectionManager.setValidateAfterInactivity(2000);
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(65000) // 服务器返回数据(response)的时间，超时抛出read timeout
                .setConnectTimeout(5000) // 连接上服务器(握手成功)的时间，超时抛出connect timeout
                .setConnectionRequestTimeout(1000)// 从连接池中获取连接的超时时间，超时抛出ConnectionPoolTimeoutException
                .build();
        HttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).setConnectionManager(connectionManager).build();

        HttpComponentsClientHttpRequestFactory httpRequestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);




        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        //  restTemplate.setMessageConverters(messageConverters);
        HttpHeaders httpHeaders = new HttpHeaders();


        HttpEntity<JSONObject> httpEntity = new HttpEntity<>(httpHeaders);
        String url = "https://www.baifubao.com/callback?cmd=1059&callback=phone&phone=15515091290";
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
        System.out.println(responseEntity.getBody());

    }

    @Test
    public void test() {
        int i1a = 100;
        int i1b = 100;
        int i2a = 200;
        int i2b = 200;
        Integer in1a = 100;
        Integer in1b = 100;
        Integer in2a = 200;
        Integer in2b = 200;

        System.out.println(i1a == i1b);
        System.out.println(i2a == i2b);
        System.out.println(in1a == in1b);
        System.out.println(in2a == in2b);
        System.out.println(i2a == in2a);
    }

}
