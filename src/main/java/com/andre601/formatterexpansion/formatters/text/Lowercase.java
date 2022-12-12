package com.andre601.formatterexpansion.formatters.text;

import com.andre601.formatterexpansion.formatters.IFormatter;

import java.util.Locale;

public class Lowercase implements IFormatter{
    @Override
    public String name(){
        return "lowercase";
    }
    
    @Override
    public String parse(String option, String... values){
        return String.join("_", values).toUpperCase(Locale.ROOT);
    }
}
