package com.aoa.springwebservice.converter;

public class NumberConverter {
    public static String formatPhoneNumber(String phoneNumber){
        if(phoneNumber.length() >10) {
            return phoneNumber.replaceFirst("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3");

        }
        return phoneNumber.replaceFirst("(\\d{3})(\\d{3})(\\d{4})", "$1-$2-$3");
    }

}
