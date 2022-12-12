package com.andre601.formatterexpansion.utils;

import java.util.Arrays;
import java.util.StringJoiner;

public class StringUtils{
    
    public static String[] getSplit(String text, String split, int length){
        return Arrays.copyOf(text.split(split,length), length);
    }
    
    public static int parseNumber(String value, int def){
        int number;
        try{
            number = Integer.parseInt(value);
        }catch(NumberFormatException ex){
            number = def;
        }
    
        return Math.min(number, def);
    }
    
    public static String merge(int startIndex, String... inputs){
        StringJoiner joiner = new StringJoiner("_");
        for(int i = startIndex; i < inputs.length; i++)
            joiner.add(inputs[i]);
        
        return joiner.toString();
    }
    
    public static boolean isNullOrEmpty(String... strings){
        for(String s : strings){
            if(s == null || s.isEmpty())
                return true;
        }
        
        return false;
    }
}
