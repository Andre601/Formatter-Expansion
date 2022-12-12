package com.andre601.formatterexpansion.formatters.text;

import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.StringUtils;

public class Replace implements IFormatter{
    @Override
    public String name(){
        return "replace";
    }
    
    @Override
    public String parse(String option, String... values){
        if(values.length < 3)
            return null;
        
        String target = values[0].replace("{{u}}", "_");
        String replacement = values[1].replace("{{u}}", "_");
        
        return StringUtils.merge(2, values).replace(target, replacement);
    }
}
