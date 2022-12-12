package com.andre601.formatterexpansion.formatters.number;

import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.NumberUtils;
import com.andre601.formatterexpansion.utils.StringUtils;

public class Time implements IFormatter{
    @Override
    public String name(){
        return "time";
    }
    
    @Override
    public String parse(String option, String... values){
        if(values.length == 1 || !StringUtils.isNullOrEmpty(values[0]))
            return NumberUtils.formatTime(values[0], "fromSeconds");
        
        return NumberUtils.formatTime(StringUtils.merge(1, values), values[0]);
    }
}
