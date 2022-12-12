package com.andre601.formatterexpansion.formatters.text;

import com.andre601.formatterexpansion.formatters.IFormatter;

import java.util.*;

public class TextFormatter implements IFormatter{
    
    private final List<IFormatter> subFormatters;
    
    public TextFormatter(){
        this.subFormatters = Arrays.asList(
            new Length(),
            new Lowercase(),
            new Replace(),
            new Substring(),
            new Uppercase()
        );
    }
    
    @Override
    public String name(){
        return "text";
    }
    
    @Override
    public String parse(String option, String... values){
        for(IFormatter subFormatter : subFormatters){
            if(subFormatter.name().equalsIgnoreCase(option))
                return subFormatter.parse(option, values);
        }
        
        return null;
    }
}
