package com.harsh.urlshortener.util;

public class Base62Encoder {

    private static final String BASE62="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE= 62;

    public static String encode(Long number){
        if(number==0){
            return String.valueOf(BASE62.charAt(0));
        }

        StringBuilder encoded = new StringBuilder();

        while(number>0){
            int remainder=(int) (number%BASE);
            encoded.append(BASE62.charAt(remainder));
            number=number/BASE;
        }

        return encoded.reverse().toString();
    }
}
