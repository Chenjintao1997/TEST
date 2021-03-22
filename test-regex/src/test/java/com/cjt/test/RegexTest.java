package com.cjt.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: chenjintao
 * @Date: 2020-03-16 15:05
 */
@RunWith(JUnit4.class)
public class RegexTest {
    @Test
    public void test1() {
        String str = "20190904-2-测试";
        String regex = "^\\d{8}-(\\d+)-\\S+$";
        System.out.println(str.matches(regex));
        System.out.println(str.replaceAll(regex, "$1"));
    }

    @Test
    public void test2() {
        String str = "123";
        String str1 = "陈锦涛";
        String str2 = "陈1";
        String regex = "[\u4e00-\u9fa5a-zA-Z]{2,4}"; //匹配汉字+字母
//        boolean boo1 = !str.matches("^[\\u4e00-\\u9fa5a-zA-Z]+$");
        boolean boo2 = str1.matches(regex);
//        System.out.println(boo1);
        boolean boo3 = str2.matches(regex);
        System.out.println(boo2);
        System.out.println(boo3);
    }

    @Test
    public void test3() {
        String regex1 = "^1[\\d]{10}$";
        String regex = "^1[\\d]{10}/\\|\"$";
        String str1 = "15515091290/|\"";
        System.out.println(str1.matches(regex));
    }

    @Test
    public void test4() {
        //校验包含数字大小写字母最少8位的祖父
        String regex = "^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{8,16}$";   //https://www.runoob.com/regexp/regexp-metachar.html   https://www.cnblogs.com/for917157ever/p/5126772.html
        String str1 = "12345678";
        String str2 = "123abcABC";
        String str3 = "9o0p(O)P";
        String str4 = "123abcagc";
        String str5 = "abcABC123";
        String str6 = "ABCabc123";
        String str7 = "123abcABC123456789";
        System.out.println(str1.matches(regex));
        System.out.println(str2.matches(regex));
        System.out.println(str3.matches(regex));
        System.out.println(str4.matches(regex));
        System.out.println(str5.matches(regex));
        System.out.println(str6.matches(regex));
        System.out.println(str7.matches(regex));
    }

    @Test
    public void test5() {
        String str = "123";
        String str1 = "陈锦涛1";
        String regex = "^[\\u4e00-\\u9fa5a-zA-Z]{2,4}$"; //匹配汉字+字母
//        boolean boo1 = !str.matches("^[\\u4e00-\\u9fa5a-zA-Z]+$");
        boolean boo2 = str1.matches(regex);
//        System.out.println(boo1);
        System.out.println(boo2);
    }

    @Test
    public void test6() {
        String str1 = null;
        Pattern pattern = Pattern.compile(".*\\d+.*");
        System.out.println(pattern.matcher(str1).matches());
        System.out.println(str1.matches(".*\\d+.*"));
    }

    @Test
    public void test7() {
        //校验包含大写小写字母数字特殊字符 不包含空格 10-16位的规则 .
        String regex = "^(?!.*[\\s].*)(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*)(?=.*[！@#￥%……&*（）_\\-+=?/\\~!$^()].*).{10,16}$";   //https://www.runoob.com/regexp/regexp-metachar.html   https://www.cnblogs.com/for917157ever/p/5126772.html
        String str1 = "1234567890";
        String str2 = "123abcABCdf";
        String str3 = "9o0p(O)P123";
        String str4 = "123abcagc123";
        String str5 = "abcABC123123";
        String str6 = "ABCabc123213";
        String str7 = "123abcABC123456789";
        String str8 = "1qaz!QAZ1234";
        String str9 = "1qaz!QAZ12 34";
        String str10 = "1qaz!QAZ12 34";
        String str11 = "1qaz!QAZ12  34";
        String str12 = "1qaz.QAZ1234";
        System.out.println(str1.matches(regex));
        System.out.println(str2.matches(regex));
        System.out.println(str3.matches(regex));
        System.out.println(str4.matches(regex));
        System.out.println(str5.matches(regex));
        System.out.println(str6.matches(regex));
        System.out.println(str7.matches(regex));
        System.out.println(str8.matches(regex));
        System.out.println(str9.matches(regex));
        System.out.println(str10.matches(regex));
        System.out.println(str11.matches(regex));
        System.out.println(str12.matches(regex));
    }

    @Test
    public void getDateByDataProviderFileName() {
        //String fileName = "i_10000_2020042712_VGOP1-R2.10-98201_00_001.dat";
        String fileName = "i_10000_2020042712_VGOP1-R2.10-98201_00_001.dat";
        //String regex = "^\\w{1,}_\\w{1,}_(\\w{1,})_.*_\\d{1,}\\.\\w{1,}$";
        String regex = "^\\w{1,}_\\w{1,}_(\\w{1,})_.*_\\d{1,}\\.\\w{1,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fileName);
        //System.out.println(matcher.find());
        String result = matcher.group(1);
        System.out.println(result);
    }


    @Test
    public void testWeChat() {
        String str = "Mozilla/5.0 (Linux; Android 10; SM-G9750 Build/QP1A.190711.020; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/77.0.3865.120 MQQBrowser/6.2 TBS/045227 Mobile Safari/537.36 MMWEBID/8448 MicroMessenger/7.0.16.1700(0x2700103F) Process/tools WeChat/arm64 NetType/WIFI Language/zh_CN ABI/arm64";
        String regex = ".*micromessenger.*wechat.*";

        System.out.println(str.toLowerCase().matches(regex));

    }

    @Test
    public void testPhone() {
        String regex = "0\\d{2,3}-\\d{7,8}";
        String regex1 = "^(((\\d{2})?0\\d{2,3}\\d{7,8})|((\\d{2})?(\\d{2,3})?([1][3456789][0-9]\\d{8})))$";
        String regex2 = "^(((\\+\\d{2}-)?0\\d{2,3}-\\d{7,8})|((\\+\\d{2}-)?(\\d{2,3}-)?([1][34578][0-9]\\d{8})))$";

        String str1 = "0372";
        String str2 = "00019915091290";
        String str3 = "0372-6008990";
        str1.matches(regex);
        str1.matches(regex1);
        System.out.println(str2.matches(regex1));
        System.out.println(str3.matches(regex2));
    }

    @Test
    public void test8(){
        //校验包含大写小写字母数字特殊字符 不包含空格 10-16位的规则 .
        String regex = "^(?!.*[\\s].*)(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*)(?=.*\\W.*).{10,16}$";

        String str1 = "1234567890";
        String str2 = "123abcABCdf";
        String str3 = "9o0p(O)P123";
        String str4 = "123abcagc123";
        String str5 = "abcABC123123";
        String str6 = "ABCabc123213";
        String str7 = "123abcABC123456789";
        String str8 = "1qaz!QAZ1234";
        String str9 = "1qaz!QAZ12 34";
        String str10 = "1qaz!QAZ12 34";
        String str11 = "1qaz!QAZ12  34";
        String str12 = "1qaz.QAZ1234";
        String str13 = "Qwertyui.123";
        System.out.println("1:"+str1.matches(regex));
        System.out.println("2:"+str2.matches(regex));
        System.out.println("3:"+str3.matches(regex));
        System.out.println("4:"+str4.matches(regex));
        System.out.println("5:"+str5.matches(regex));
        System.out.println("6:"+str6.matches(regex));
        System.out.println("7:"+str7.matches(regex));
        System.out.println("8:"+str8.matches(regex));
        System.out.println("9:"+str9.matches(regex));
        System.out.println("10:"+str10.matches(regex));
        System.out.println("11:"+str11.matches(regex));
        System.out.println("12:"+str12.matches(regex));
        System.out.println("13:"+str13.matches(regex));
    }

    @Test
    public void test9(){
        String str1 = "1231f1f1f12,f21f12f1,f12f21f,12f1ff12,";
        String str2 = "12312f1f1,f21f12f1,f12f12f";
        String str3 = "12321f21f1f";

        String regex = "(.*),$";
        System.out.println(str1.replaceAll(regex,"$1"));
        System.out.println(str2.replaceAll(regex,"$2"));
        System.out.println(str3.replaceAll(regex,"$3"));
    }
}
