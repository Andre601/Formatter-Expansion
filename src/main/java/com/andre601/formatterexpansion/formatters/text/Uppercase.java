package com.andre601.formatterexpansion.formatters.text;

import com.andre601.formatterexpansion.formatters.IFormatter;

import java.util.Locale;

public class Uppercase implements IFormatter{
    @Override
    public String name(){
        return "uppercase";
    }
    
    @Override
    public String parse(String raw, String option, String... values){
        return String.join("_", values).toUpperCase(Locale.ROOT);
    }
}
