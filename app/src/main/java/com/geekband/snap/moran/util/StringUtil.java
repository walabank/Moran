package com.geekband.snap.moran.util;

import java.security.MessageDigest;
import java.util.regex.Pattern;

public class StringUtil {
    private final static Pattern emailer
            = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

    public static boolean isEmpty(String input) {
        if (input == null || "".equals(input))
            return true;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
                return false;
            }
        }
        return true;
    }

    public static boolean isEmail(String email) {
        if (email == null || email.trim().length() == 0)
            return false;
        return emailer.matcher(email).matches();
    }
   public static boolean isPasswordValid(String password){
        if(password.length()>= 6 && (password.length()<=20)){
            return true;
        }else {
            return false;
        }
   }
    public static String toDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    public static String encryptPhoneNumber(String phone) {
        if (phone == null) {
            return "*****";
        }
        if (phone.length() < 11) {
            return phone;
        }
        return phone.substring(0, 3) + "*****" + phone.substring(8, 11);
    }

    public static String convertUserDeliveryAddress(String address) {
        String[] info = address.split("#");
        StringBuffer text = new StringBuffer();
        text
                .append(info[1]) //province
                .append(info[2]) //city
                .append(info[3]) //zone
                .append(info[4]) //street
                .append("\n")
                .append(info[0]) //name
                .append(" ")
                .append(info[6]) // phone
        ;
        return text.toString();
    }

    public static String getMD5(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("md5");
            byte[] results = digest.digest(password.getBytes());
            StringBuilder stringBuilder = new StringBuilder();
            for(byte b :results){
                int number = b&0xff;
                String hex = Integer.toHexString(number);
                if(hex.length()==1){
                    stringBuilder.append("0");
                }
                stringBuilder.append(hex);
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}