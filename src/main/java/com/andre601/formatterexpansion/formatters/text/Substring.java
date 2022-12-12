package com.andre601.formatterexpansion.formatters.text;

import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.StringUtils;

public class Substring implements IFormatter{
    @Override
    public String name(){
        return "substring";
    }
    
    @Override
    public String parse(String option, String... values){
        if(values.length < 2 || StringUtils.isNullOrEmpty(values[0], values[1]))
            return null;
        
        String[] ranges = StringUtils.getSplit(values[0], ":", 2);
        int start = 0;
        int end = values[1].length();
        
        if(!StringUtils.isNullOrEmpty(ranges[0]))
            start = StringUtils.parseNumber(ranges[0], start);
        
        if(!StringUtils.isNullOrEmpty(ranges[1]))
            end = StringUtils.parseNumber(ranges[1], end);
        
        return values[1].substring(start, end);
    }
}
