package com.andre601.formatterexpansion.utils;

import java.math.BigDecimal;

public class NumberUtils{
    
    public static int parseNumber(String value){
        try{
            return Integer.parseInt(value);
        }catch(NumberFormatException ex){
            return -1;
        }
    }
    
    public static Long optLong(String value){
        try{
            return Long.parseLong(value);
        }catch(NumberFormatException ex){
            return null;
        }
    }
    
    public static BigDecimal getBigDecimal(String number){
        try{
            return new BigDecimal(number);
        }catch(NumberFormatException ex){
            return null;
        }
    }
}
