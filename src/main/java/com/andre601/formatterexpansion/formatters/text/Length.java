package com.andre601.formatterexpansion.formatters.text;

import com.andre601.formatterexpansion.formatters.IFormatter;

public class Length implements IFormatter{
    @Override
    public String name(){
        return "length";
    }
    
    @Override
    public String parse(String raw, String option, String... values){
        return String.valueOf(String.join("_", values).length());
    }
}
