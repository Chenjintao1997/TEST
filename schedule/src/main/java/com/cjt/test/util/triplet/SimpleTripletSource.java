package com.cjt.test.util.triplet;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class SimpleTripletSource implements TripletSource {

    //private final static String delim = " \t\n\r\f,，|";
    //todo  不再使用StringTokenizer，reason：1.jdk官方文档声明该类为遗留类只为了保证后期jdk版本兼容性，推荐使用split替代；2.StringTokenizer会使用连续的分隔符分隔，分隔后不会存在null值
    private String delim;

    private String charsetName;


    BufferedReader input;
    String line;
    List<String> fieldNames;

    public SimpleTripletSource(File file, String charsetName, String delim) {
        try {
            this.delim = delim;
            this.charsetName = charsetName;

            // input = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(file))));
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SimpleTripletSource(InputStream is, String charsetName, String delim) {
        try {
            this.delim = delim;
            this.charsetName = charsetName;

            //input = new BufferedReader(new InputStreamReader(new BOMInputStream(is)));
            input = new BufferedReader(new InputStreamReader(is, charsetName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SimpleTripletSource(File file) {
        try {
            //此处使用ASCII码0X80（‘€’） 因为过来的数据的€并不是GBK编码的，所以实际上只要是用128~256（-1~-128）（十进制）区间的某个值来解码或者任意字符只要指定的编码集不认识它，作为分隔符去分隔整行数据都可以成功
            //this.delim = new String(Hex.decodeHex("80".toCharArray()));
            this.delim = "\\|";  //约定的规范已修改
            this.charsetName = "UTF-8";

            // input = new BufferedReader(new InputStreamReader(new BOMInputStream(new FileInputStream(file))));
            input = new BufferedReader(new InputStreamReader(new FileInputStream(file), charsetName));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public SimpleTripletSource(InputStream is) {
        try {
            //此处使用ASCII码0X80（‘€’） 因为过来的数据的€并不是GBK编码的，所以实际上只要是用128~256（-1~-128）（十进制）区间的某个值来解码或者任意字符只要指定的编码集不认识它，作为分隔符去分隔整行数据都可以成功
            this.delim = "\\|";  //约定的规范已修改
            this.charsetName = "UTF-8";
            //input = new BufferedReader(new InputStreamReader(new BOMInputStream(is)));
            input = new BufferedReader(new InputStreamReader(is, charsetName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Iterator<Triplet> iterator() {
        return new Iterator<Triplet>() {

            @Override
            public boolean hasNext() {
                if (line == null) {
                    try {
                        line = input.readLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return line != null;
            }

            @Override
            public Triplet next() {
                Triplet result = null;
                if (line != null) {
                    // if (fieldNames == null) fieldNames = guessFieldNames(line);
                    //result = fetchTriplet(line);
                    result = fetchBaseTriplet(line);
                    line = null;
                }
                return result;
            }
        };
    }

//    public static List<String> guessFieldNames(String s) {
//        List<String> result = new ArrayList<>();
//        int i = 0;
//        boolean phoneNumberFound = false, realNameFound = false, identityNumberFound = false;
//        for (StringTokenizer tokens = new StringTokenizer(s, delim); tokens.hasMoreElements(); i++) {
//            String token = tokens.nextToken();
//            if (!phoneNumberFound && Triplet.PHONE_NUMBER_PATTERN.matcher(token).matches()) {
//                result.add(Triplet.PHONE_NUMBER);
//            } else if (!realNameFound && Triplet.REAL_NAME_PATTERN.matcher(token).matches()) {
//                result.add(Triplet.REAL_NAME);
//            } else if (!identityNumberFound && Triplet.IDENTITY_NUMBER_PATTERN.matcher(token).matches()) {
//                result.add(Triplet.IDENTITY_NUMBER);
//            } else {
//                result.add("field" + i);
//            }
//        }
//        return result;
//    }
//
//    public static Triplet fetchTriplet(String s, List<String> fieldNames) {
//        Triplet result = new Triplet();
//        int i = 0;
//        for (StringTokenizer tokens = new StringTokenizer(s, delim); tokens.hasMoreElements(); i++) {
//            String token = tokens.nextToken();
//            result.put(fieldNames.get(i), token);
//        }
//        return result;
//    }

    @Override
    public void close() throws IOException {
        input.close();
    }

//    public static Triplet fetchTriplet(String s) {
//        int i = 0;
//        Triplet result = new Triplet();
//        boolean phoneNumberFound = false, realNameFound = false, identityNumberFound = false;
//        for (StringTokenizer tokens = new StringTokenizer(s, delim); tokens.hasMoreElements(); i++) {
//            String token = tokens.nextToken();
//            if (!phoneNumberFound && Triplet.PHONE_NUMBER_PATTERN.matcher(token).matches()) {
//                result.put(Triplet.PHONE_NUMBER, token);
//                phoneNumberFound = true;
//            } else if (!realNameFound && Triplet.REAL_NAME_PATTERN.matcher(token).matches()) {
//                result.put(Triplet.REAL_NAME, token);
//                realNameFound = true;
//            } else if (!identityNumberFound && Triplet.IDENTITY_NUMBER_PATTERN.matcher(token).matches()) {
//                result.put(Triplet.IDENTITY_NUMBER, token);
//                identityNumberFound = true;
//            } else {
//                result.put("field" + i, token);
//            }
//        }
//        return result;
//    }

    public Triplet fetchBaseTriplet(String s) {
        Triplet result = new Triplet();
        String[] tokens = s.split(delim);
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            result.put("field" + i, token);
        }
        return result;
    }

//    public static Triplet fetchBaseTriplet(String s) {
//        Triplet result = new Triplet();
//        int i = 0;
//        StringTokenizer tokens = new StringTokenizer(s, delim);
//        tokens.setEmptyTokenAsNull(true);
//        tokens.setIgnoreEmptyTokens(false);
//        for (; tokens.hasNext(); i++) {
//            String token = tokens.next();
//            result.put("field" + i, token);
//        }
//        return result;
//    }

//    public static Triplet fetchBaseTriplet(String s) {
//        Triplet result = new Triplet();
//        int j = 0;
//        String lastToken = null;
//        StringTokenizer tokens = new StringTokenizer(s, delim, true);
//        while (tokens.hasMoreElements()) {
//            String token = tokens.nextToken();
//            if (token.equalsIgnoreCase(delim)) {
//                if (token.equalsIgnoreCase(lastToken)) {
//                    result.put("field" + j, null);
//                    j++;
//                }
//            } else {
//                result.put("field" + j, token);
//                j++;
//            }
//            lastToken = token;
//        }
//        return result;
//    }

}

