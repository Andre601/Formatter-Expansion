package com.andre601.formatterexpansion.utils;

import java.util.Arrays;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class StringUtils{
    
    public static String[] getSplit(String text, String split, int length){
        return Arrays.copyOf(text.split(split,length), length);
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
    
    public static TimeUnit getTimeUnit(String value){
        switch(value.toLowerCase(Locale.ROOT)){
            case "days":
            case "day":
                return TimeUnit.DAYS;
            
            case "fromhours":
            case "fromhrs":
            
            case "hours":
            case "hour":
            case "hrs":
                return TimeUnit.HOURS;
            
            case "fromminutes":
            case "frommins":
            
            case "minutes":
            case "minute":
            case "mins":
            case "min":
                return TimeUnit.MINUTES;
            
            case "fromseconds":
            case "fromsecs":
            
            case "seconds":
            case "second":
            case "secs":
            case "sec":
                return TimeUnit.SECONDS;
            
            case "frommilliseconds":
            case "fromms":
            
            case "milliseconds":
            case "millisecond":
            case "millis":
            case "ms":
                return TimeUnit.MILLISECONDS;
            
            default:
                return null;
        }
    }
}
