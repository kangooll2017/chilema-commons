package com.chilema.commons.test;

import java.security.MessageDigest;

public class MD5Util {

    public static String digest16(String inStr) {
        return digest(inStr, 16);
    }

    public static String digest(String inStr) {
        return digest(inStr, 32);
    }

    private static String digest(String inStr, int rang) {
        //MessageDigest：JDK带的消息加密类
        MessageDigest md5 = null;
        if (MyStringUtils.isEmpty(inStr)) {
            return "";
        }

        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++) {
            byteArray[i] = (byte) charArray[i];
        }

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuilder hexValue = new StringBuilder();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        if (rang == 32) {
            return hexValue.toString();
        } else {
            return hexValue.toString().substring(8, 24);
        }
    }

    public static void main(String args[]) {
        String s = new String("admin");
        String digest1 = digest(s);
        String digest2 = digest(s);
        System.out.println(digest1);
        System.out.println(digest2);
        
        //MD5+盐值加密  shiro
        
        String pass = new String("123456");
        System.out.println();
        String str = digest(digest(pass));
        System.out.println(str);
        //二次MD5加密；
        //用户输入 admin  123456
        //数据库取出的 admin  csakjldjsakdhsaudiasnm
        //1、将用户输入的用户名密码拿到
        String dsa = MD5Util.digest("123456");
        //2、去数据库查用户，使用用户带来的用户名，和加密后的密码进行查询;
        
        
        /*
         密码加密的流程： 当用户注册的时候，密码会以明文的形式保存到数据库，
         导致数据库的管理员可以轻易看到用户的帐号和密码；
             于是采用加密的方式，将密码进行加密，例如输入123456， 经过加密转化为16、32位的字符串
             保存到数据库，当用户再次登录的时候，再将其数据的密码，加密后和数据库的加密码进行比较
             ；
             一些简单的密码，加密后的格式为固定的不安全，可以经过多次加密
             或者 加上一个UUID，但是每次获取的UUID都需要保存到数据库，以便登录的时候可以通过密码和UUID
             再次获取到加密的密码
         
         
         * */
    }
}
