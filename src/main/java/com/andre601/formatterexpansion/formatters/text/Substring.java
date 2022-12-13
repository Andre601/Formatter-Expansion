package com.andre601.formatterexpansion.formatters.text;

import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.NumberUtils;
import com.andre601.formatterexpansion.utils.StringUtils;

public class Substring implements IFormatter{
    @Override
    public String name(){
        return "substring";
    }
    
    @Override
    public String parse(String option, String... values){
        if(values.length < 2)
            return null;
        
        String[] ranges = StringUtils.getSplit(values[0], ":", 2);
        
        int start = NumberUtils.parseNumber(ranges[0]);
        int end = NumberUtils.parseNumber(ranges[1]);
        
        return subString(StringUtils.merge(1, "_", values), start, end);
    }
    
    private String subString(String text, int start, int end){
        if(start < 0)
            start = 0; // Make sure start isn't negative.
        
        if(end > text.length())
            end = text.length(); // Make sure end isn't larger than the string itself.
        
        return text.substring(start, end);
    }
}
