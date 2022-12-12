package com.andre601.formatterexpansion.formatters.number;

import com.andre601.formatterexpansion.FormatterExpansion;
import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.NumberUtils;
import com.andre601.formatterexpansion.utils.StringUtils;

public class Format implements IFormatter{
    @Override
    public String name(){
        return "format";
    }
    
    @Override
    public String parse(String option, String... values){
        if(values.length == 1)
            return NumberUtils.formatNumber(values[0]);
        
        String[] options = StringUtils.getSplit(values[0], ":", 2);
        
        String locale = FormatterExpansion.getExpansion().getString("locale", "en-US");
        String pattern = FormatterExpansion.getExpansion().getString("format", "#,###,###.##");
        
        if(!StringUtils.isNullOrEmpty(options[0]))
            locale = options[0];
        
        if(!StringUtils.isNullOrEmpty(options[1]))
            pattern = options[1];
        
        return NumberUtils.formatNumber(values[1], locale, pattern);
    }
}
