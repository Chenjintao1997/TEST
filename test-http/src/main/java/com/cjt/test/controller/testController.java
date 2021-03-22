package com.cjt.test.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cjt.test.bean.MessageApiReturnData;
import com.cjt.test.bean.TenantInfoData;
import com.cjt.test.bean.TenantInfoResult;
import com.cjt.test.bean.TestMessageParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: chenjintao
 * @Date: 2020-03-04 18:12
 */
@RestController
public class testController {

    @PostMapping("/test/message/api")
    public Object test1(@RequestBody TestMessageParam testMessageParam) {
        System.out.println("参数：" + testMessageParam.toString());
        MessageApiReturnData messageApiReturnData = new MessageApiReturnData();
        messageApiReturnData.setRtnCode("0");
        messageApiReturnData.setRtnMsg("接口调用成功");
        System.out.println("返回值" + messageApiReturnData);
        return messageApiReturnData;
    }
//
//    @PostMapping("/OutBound/services/jldsj/getAllCompany")
//    public Object test2() {
//
//        Map<String,Object> jsonObject = new HashMap<>();
//        List<Map<String,Object>> list = new ArrayList<>();
//        jsonObject.put("code", "0000");
//        jsonObject.put("resMsg", "查询成功");
//        jsonObject.put("data", list);
//
//        for (int i = 1; i < +10; i++) {
//            Map<String,Object> map = new HashMap<>();
//            map.put("companyid", "tenantId" + i);
//            map.put("companyname", "tenantName" + i);
//
//            list.add(map);
//        }
//        return jsonObject;
//    }


    @PostMapping("/OutBound/services/jldsj/getAllCompany")
    public Object test2() {

        TenantInfoResult tenantInfoResult = new TenantInfoResult();
        List<TenantInfoData> list = new ArrayList<>();
        tenantInfoResult.setCode("0000");
        tenantInfoResult.setResMsg("查询成功");
        tenantInfoResult.setData(list);

        for (int i = 1; i <= 10; i++) {
            TenantInfoData tenantInfoData = new TenantInfoData();
            tenantInfoData.setCompanyid("tenantId" + (i));
            tenantInfoData.setCompanyname("tenantName" + i);

            list.add(tenantInfoData);
        }
        return tenantInfoResult;
    }

    @PostMapping("/OutBound/services/jldsj/getAllCompany/a")
    public Object test2a() {

        JSONObject jsonObject = new JSONObject();
        JSONArray list = new JSONArray();
        jsonObject.put("code", "0000");
        jsonObject.put("resMsg", "查询成功");
        jsonObject.put("data", list);

        for (int i = 1; i <= 10; i++) {
            JSONObject tenantInfoData = new JSONObject();
            tenantInfoData.put("companyid", "tenantId" + (i));
            tenantInfoData.put("companyname", "tenantName" + i);

            list.add(tenantInfoData);
        }
        return jsonObject;
    }

    @PostMapping("/OutBound/services/jldsj/batchImportDatas")
    public Object test3(@RequestBody Object param) {
        System.out.println(param);

        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        jsonObject.put("code", "0000");
        jsonObject.put("resMsg", "查询成功");
        jsonObject.put("result", jsonArray);

        for (int i = 1; i < +10; i++) {
            JSONObject object = new JSONObject();
            object.put("companyid", "tenantId" + i);
            object.put("status", "0");

            jsonArray.add(object);
        }

        return jsonObject;
    }


    @GetMapping("/netflix/set/cookie")
    public void netflixSetCookie(HttpServletResponse response) {

        test(response, 0);
    }


    public void test(HttpServletResponse response, int i) {

        String cookieJson = "[\n" +
                "{\n" +
                "    \"domain\": \"netflix.com\",\n" +
                "    \"hostOnly\": false,\n" +
                "    \"httpOnly\": false,\n" +
                "    \"name\": \"clSharedContext\",\n" +
                "    \"path\": \"/\",\n" +
                "    \"sameSite\": \"unspecified\",\n" +
                "    \"secure\": false,\n" +
                "    \"session\": true,\n" +
                "    \"storeId\": \"1\",\n" +
                "    \"value\": \"9da97e3b-f5ea-4d60-8327-5183ad02051b\",\n" +
                "    \"id\": 1\n" +
                "},\n" +
                "{\n" +
                "    \"domain\": \".netflix.com\",\n" +
                "    \"expirationDate\": 1587813075.717221,\n" +
                "    \"hostOnly\": false,\n" +
                "    \"httpOnly\": false,\n" +
                "    \"name\": \"flwssn\",\n" +
                "    \"path\": \"/\",\n" +
                "    \"sameSite\": \"unspecified\",\n" +
                "    \"secure\": false,\n" +
                "    \"session\": false,\n" +
                "    \"storeId\": \"1\",\n" +
                "    \"value\": \"31b7222b-84b7-4992-9934-b2949e5f5db5\",\n" +
                "    \"id\": 2\n" +
                "},\n" +
                "{\n" +
                "    \"domain\": \".netflix.com\",\n" +
                "    \"expirationDate\": 1619338283.828898,\n" +
                "    \"hostOnly\": false,\n" +
                "    \"httpOnly\": false,\n" +
                "    \"name\": \"memclid\",\n" +
                "    \"path\": \"/\",\n" +
                "    \"sameSite\": \"unspecified\",\n" +
                "    \"secure\": false,\n" +
                "    \"session\": false,\n" +
                "    \"storeId\": \"1\",\n" +
                "    \"value\": \"590483aa-692f-483d-89d2-f789aee67890\",\n" +
                "    \"id\": 3\n" +
                "},\n" +
                "{\n" +
                "    \"domain\": \".netflix.com\",\n" +
                "    \"expirationDate\": 1619338275.717299,\n" +
                "    \"hostOnly\": false,\n" +
                "    \"httpOnly\": true,\n" +
                "    \"name\": \"NetflixId\",\n" +
                "    \"path\": \"/\",\n" +
                "    \"sameSite\": \"lax\",\n" +
                "    \"secure\": false,\n" +
                "    \"session\": false,\n" +
                "    \"storeId\": \"1\",\n" +
                "    \"value\": \"v%3D2%26ct%3DBQAOAAEBEPhsouIFt-32hYNASC9a0vuB0Oweinucyp3pK3B06NEf3QbabzDrHUdke3swHSZpAMsO2UZEw6GRX1wAnHsjaYruKBEmWbbx44l_XhPFG_vAZtyDlk2nTp19SNLsvlDARgZ0I_XJsmx0eQcOyC1O2K0fVgUVno3lnCiKy1ki4I3sNdydLTPCLrnGlbaASlYxOZZwP8QcJXN5SOSAVA_DACJDs91K7eI9w6fSieXnUID8qbi0m39zhrqcBTkKOIdkPkdDn-Qxj-Ap9bFkHKYgGVrLV7bV3rwkyQgWOXLkLVixY4PETc0CgYo-6LZwqp49ofGWo9WGSK_lO70eDcGSAA0tU8EcHu5c59c3ijVfKjOLkeN-BoWYUy4Ij3_NXrbyhJWsCYRKW96u7aMZyPkSkiDx9jO1OajsRC0CeHcvsGa1NgRmnsgvyd2vnfTJMnvwZjmnQg_jj3hX8Xb4SVdovP_BwDLuMmKynQDJ51Rdrn-eq5n7elpqKigmgJMPWIB7A6OOk6I42m9rhZR33emLeTceqMZi58jP0yHgZOol1Ul77MdrtF00rsxfQY0KVyXbvPvZZkjEhIoA6qdsLKw8PZIIqeuYHT8hgfK5ToifdcpCVi_SFevJwWBWYyGO9ME6oXMx%26bt%3Ddbl%26ch%3DAQEAEAABABT_9xRdIrlprv6shCxFhmJ4gP9ohE0Mu_I.%26mac%3DAQEAEAABABTJKQuiRFAYZlyvRgjZmRquiqEIg9fJbcY.\",\n" +
                "    \"id\": 4\n" +
                "},\n" +
                "{\n" +
                "    \"domain\": \".netflix.com\",\n" +
                "    \"expirationDate\": 1619338275.717261,\n" +
                "    \"hostOnly\": false,\n" +
                "    \"httpOnly\": false,\n" +
                "    \"name\": \"nfvdid\",\n" +
                "    \"path\": \"/\",\n" +
                "    \"sameSite\": \"unspecified\",\n" +
                "    \"secure\": false,\n" +
                "    \"session\": false,\n" +
                "    \"storeId\": \"1\",\n" +
                "    \"value\": \"BQFmAAEBEIxcLbm3bULgiS-apZDxgvNgben4uEWSGtdhRrbCBmTrw0A9nPr8KrEvUNEBD5E9n7HvKgK9n8fPZl1P31evIc7_U3RR_WcY0vxWDLEwpKHoG7KgBkuCYfmODW-9lFJisG49kJ_GjrEmyL3yjP9S1AFA\",\n" +
                "    \"id\": 5\n" +
                "},\n" +
                "{\n" +
                "    \"domain\": \".netflix.com\",\n" +
                "    \"expirationDate\": 1619338275.717279,\n" +
                "    \"hostOnly\": false,\n" +
                "    \"httpOnly\": true,\n" +
                "    \"name\": \"SecureNetflixId\",\n" +
                "    \"path\": \"/\",\n" +
                "    \"sameSite\": \"lax\",\n" +
                "    \"secure\": true,\n" +
                "    \"session\": false,\n" +
                "    \"storeId\": \"1\",\n" +
                "    \"value\": \"v%3D2%26mac%3DAQEAEQABABTdCqWfqYUxfWnOMVpDkYGSui27z3ByqmU.%26dt%3D1587802274464\",\n" +
                "    \"id\": 6\n" +
                "},\n" +
                "{\n" +
                "    \"domain\": \".www.netflix.com\",\n" +
                "    \"hostOnly\": false,\n" +
                "    \"httpOnly\": false,\n" +
                "    \"name\": \"cL\",\n" +
                "    \"path\": \"/\",\n" +
                "    \"sameSite\": \"unspecified\",\n" +
                "    \"secure\": false,\n" +
                "    \"session\": true,\n" +
                "    \"storeId\": \"1\",\n" +
                "    \"value\": \"1587802277551%7C158780226284311307%7C158780226210034950%7C%7C4%7Cnull\",\n" +
                "    \"id\": 7\n" +
                "}\n" +
                "]";


        JSONArray list = JSON.parseArray(cookieJson);
        int size = list.size();
//
//        for (int i = 0; i < size; i++) {
//            JSONObject cookieObj = list.getJSONObject(i);
//
//            String domain = cookieObj.getString("domain");
//            boolean hostOnly = cookieObj.getBoolean("hostOnly");
//            boolean httpOnly = cookieObj.getBoolean("httpOnly");
//            String name = cookieObj.getString("name");
//            String path = cookieObj.getString("path");
//            String sameSite = cookieObj.getString("sameSite");
//            boolean secure = cookieObj.getBoolean("secure");
//            boolean session = cookieObj.getBoolean("session");
//            String storeId = cookieObj.getString("storeId");
//            String value = cookieObj.getString("value");
//            String id = cookieObj.getString("id");
//
//            Cookie cookie = new Cookie(name, value);
//            cookie.setDomain(domain);
//            cookie.setHttpOnly(hostOnly);
//            cookie.setPath(path);
//            cookie.setSecure(secure);
//
//            response.addCookie(cookie);
//        }
        JSONObject cookieObj = list.getJSONObject(i);
        String domain = cookieObj.getString("domain");
        boolean hostOnly = cookieObj.getBoolean("hostOnly");
        boolean httpOnly = cookieObj.getBoolean("httpOnly");
        String name = cookieObj.getString("name");
        String path = cookieObj.getString("path");
        String sameSite = cookieObj.getString("sameSite");
        boolean secure = cookieObj.getBoolean("secure");
        boolean session = cookieObj.getBoolean("session");
        String storeId = cookieObj.getString("storeId");
        String value = cookieObj.getString("value");
        String id = cookieObj.getString("id");
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(domain);
        cookie.setHttpOnly(hostOnly);
        cookie.setPath(path);
        cookie.setSecure(secure);
        cookie.setMaxAge(24 * 60 * 60);

        response.addCookie(cookie);
    }
}
