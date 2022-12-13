package com.andre601.formatterexpansion.utils;

import java.util.Arrays;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class StringUtils{
    
    public static String[] getSplit(String text, String split, int length){
        return Arrays.copyOf(text.split(split,length), length);
    }
    
    public static String merge(int startIndex, String delimiter, String... inputs){
        StringJoiner joiner = new StringJoiner(delimiter);
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
    
    public static TimeUnit getTimeUnit(String value){
        return switch(value.toLowerCase(Locale.ROOT)){
            case "days", "day" -> TimeUnit.DAYS;
            case "fromhours", "fromhrs", "hours", "hour", "hrs" -> TimeUnit.HOURS;
            case "fromminutes", "frommins", "minutes", "minute", "mins", "min" -> TimeUnit.MINUTES;
            case "fromseconds", "fromsecs", "seconds", "second", "secs", "sec" -> TimeUnit.SECONDS;
            case "frommilliseconds", "fromms", "milliseconds", "millisecond", "millis", "ms" -> TimeUnit.MILLISECONDS;
            default -> null;
        };
    }
}
