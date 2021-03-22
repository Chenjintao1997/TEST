package com.cjt.test.util.triplet;

import java.util.HashMap;
import java.util.regex.Pattern;

public class Triplet extends HashMap<String, String> {

    public final static Pattern PHONE_NUMBER_PATTERN = Pattern.compile("1\\d{10}");
    public final static Pattern REAL_NAME_PATTERN = Pattern.compile("[\u4E00-\u9FA5]{2,4}");
    public final static Pattern IDENTITY_NUMBER_PATTERN = Pattern.compile("[0-9Xx]{18}");

    public final static String PHONE_NUMBER = "phoneNumber";
    public final static String REAL_NAME = "realName";
    public final static String IDENTITY_NUMBER = "identityNumber";

    public String getPhoneNumber() {
        return get(PHONE_NUMBER);
    }

    public void setPhoneNumber(String phoneNumber) {
        put(PHONE_NUMBER, phoneNumber);
    }

    public String getRealName() {
        return get(REAL_NAME);
    }

    public void setRealName(String realName) {
        put(REAL_NAME, realName);
    }

    public String getIdentityNumber() {
        return get(IDENTITY_NUMBER);
    }

    public void setIdentityNumber(String identityNumber) {
        put(IDENTITY_NUMBER,  identityNumber);
    }

}
