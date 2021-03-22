import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cjt.test.bean.TestBean1;
import com.cjt.test.bean.TestBean2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

/**
 * @Author: chenjintao
 * @Date: 2019-09-30 17:56
 */
@RunWith(JUnit4.class)
public class TestClass {

    @Test
    public void test1() {
        String strJson = "{'key1':'value1','key2':[1,2,3]}";
        JSONObject jsonObject = JSON.parseObject(strJson);
        System.out.println(jsonObject);
        System.out.println(jsonObject.getString("key2"));
    }

    @Test
    public void test2() {
        String json = "";
        List<TestBean1> testBean1List = JSON.parseArray(json, TestBean1.class);
        System.out.println(testBean1List);
    }


    @Test
    public void test3() {
        String cookieJson = "[\n" +
                "{\n" +
                "    \"domain\": \".netflix.com\",\n" +
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


    }


    @Test
    public void test4() {
        String json = "[{\"field\":\"inTime\",\"fieldName\":\"签入时长\"},{\"field\":\"onlineTime\",\"fieldName\":\"上线时长\"},{\"field\":\"serviceUserTime\",\"fieldName\":\"服务用户时长\"},{\"field\":\"advTime\",\"fieldName\":\"咨询时长\"},{\"field\":\"idleTime\",\"fieldName\":\"示闲时长\"},{\"field\":\"busyTime\",\"fieldName\":\"示忙时长\"},{\"field\":\"noServiceTime\",\"fieldName\":\"无服务时长\"}]";

        Object result = JSON.parse(json);

        System.out.println(result.toString());
    }

    @Test
    public void test5() {
        String str1 = null;
        Integer i1 = null;

        String str2 = str1 + "-" + i1;
        System.out.println(str2);
    }

    @Test
    public void test6(){
        TestBean2 bean = new TestBean2();
        bean.setPhone("155150");
        String json = "{\"name\":\"陈锦涛\",\"password\":\"123456\"}";
        TestBean2 bean2 = JSON.parseObject(json,bean.getClass());
        System.out.println(bean2);
    }

    @Test
    public void test7(){
        String str = "";
        System.out.println(str.length());
    }

    @Test
    public void test8(){
        String customerTagValue = "123,afd,!!DSQ,";
        customerTagValue = customerTagValue.replaceAll("(.*),$","$1");
        System.out.println(customerTagValue);
    }

    @Test
    public void test9(){
       String str1 = null;
       String str2 = null;
       String str3 = null;
        System.out.println(str1+str2+str3);
    }
}
