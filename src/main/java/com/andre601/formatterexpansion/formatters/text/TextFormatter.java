package com.andre601.formatterexpansion.formatters.text;

import com.andre601.formatterexpansion.FormatterExpansion;
import com.andre601.formatterexpansion.formatters.IFormatter;

import java.util.Arrays;
import java.util.List;

public class TextFormatter implements IFormatter{
    
    private final List<IFormatter> subFormatters;
    
    public TextFormatter(FormatterExpansion expansion){
        this.subFormatters = Arrays.asList(
            new Length(),
            new Lowercase(),
            new Replace(expansion),
            new Substring(expansion),
            new Uppercase()
        );
    }
    
    @Override
    public String name(){
        return "text";
    }
    
    @Override
    public String parse(String raw, String option, String... values){
        for(IFormatter subFormatter : subFormatters){
            if(subFormatter.name().equalsIgnoreCase(option))
                return subFormatter.parse(raw, option, values);
        }
        
        return null;
    }
}
