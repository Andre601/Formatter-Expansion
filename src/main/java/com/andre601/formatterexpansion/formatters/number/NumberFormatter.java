package com.andre601.formatterexpansion.formatters.number;

import com.andre601.formatterexpansion.FormatterExpansion;
import com.andre601.formatterexpansion.formatters.IFormatter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class NumberFormatter implements IFormatter{
    
    private final List<IFormatter> subFormatters;
    private final FromTo fromToFormatter;
    
    public NumberFormatter(FormatterExpansion expansion){
        this.subFormatters = Arrays.asList(
            new Format(expansion),
            new Round(expansion),
            new Time(expansion)
        );
        this.fromToFormatter = new FromTo(expansion);
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
