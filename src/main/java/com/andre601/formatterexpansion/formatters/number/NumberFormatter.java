package com.andre601.formatterexpansion.formatters.number;

import com.andre601.formatterexpansion.formatters.IFormatter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class NumberFormatter implements IFormatter{
    
    private final List<IFormatter> subFormatters;
    private final FromTo fromToFormatter = new FromTo();
    
    public NumberFormatter(){
        this.subFormatters = Arrays.asList(
            new Format(),
            new Round(),
            new Time()
        );
    }
    
    @Override
    public String name(){
        return "number";
    }
    
    @Override
    public String parse(String option, String... values){
        if(option.toLowerCase(Locale.ROOT).startsWith("from:"))
            return fromToFormatter.parse(option, values);
            
        for(IFormatter subFormatter : subFormatters){
            if(subFormatter.name().equalsIgnoreCase(option))
                return subFormatter.parse(option, values);
        }
        
        return null;
    }
}
