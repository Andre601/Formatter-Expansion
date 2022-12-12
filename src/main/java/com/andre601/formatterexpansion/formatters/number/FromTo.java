package com.andre601.formatterexpansion.formatters.number;

import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.NumberUtils;
import com.andre601.formatterexpansion.utils.StringUtils;

import java.util.Locale;

public class FromTo implements IFormatter{
    @Override
    public String name(){
        return null; // Never used for this special case.
    }
    
    @Override
    public String parse(String option, String... values){
        if(values.length < 2 || !StringUtils.isNullOrEmpty(values[0], values[1]) || !values[0].toLowerCase(Locale.ROOT).startsWith("to:"))
            return null;
        
        String from = StringUtils.getSplit(option, ":", 2)[1];
        String to = StringUtils.getSplit(values[0], ":", 2)[1];
        
        if(StringUtils.isNullOrEmpty(from, to))
            return null;
    
        return NumberUtils.convert(StringUtils.merge(1, values), from, to);
    }
}
