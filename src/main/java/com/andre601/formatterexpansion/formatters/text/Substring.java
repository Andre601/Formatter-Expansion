package com.andre601.formatterexpansion.formatters.text;

import com.andre601.formatterexpansion.FormatterExpansion;
import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.NumberUtils;
import com.andre601.formatterexpansion.utils.StringUtils;
import com.andre601.formatterexpansion.utils.logging.CachedWarnHelper;

import java.util.Locale;

public class Substring implements IFormatter{
    
    private final FormatterExpansion expansion;
    
    public Substring(FormatterExpansion expansion){
        this.expansion = expansion;
    }
    
    @Override
    public String name(){
        return "substring";
    }
    
    @Override
    public String parse(String raw, String option, String... values){
        if(values.length < 2){
            CachedWarnHelper.warn(expansion, "length", raw, "Placeholder requires a [start]:[end] and <text>.");
            return null;
        }
        
        String[] ranges = StringUtils.getSplit(values[0], ":", 2);
        String text = StringUtils.merge(1, "_", values);
        
        int start = NumberUtils.parseNumber(ranges[0]);
        int end = NumberUtils.parseNumber(ranges[1]);
        
        // start isn't a number and start text isn't empty: Try getting index of provided text.
        if(start == -1 && !ranges[0].isEmpty())
            start = text.toLowerCase(Locale.ROOT).indexOf(ranges[0].toLowerCase(Locale.ROOT));
    
        // end isn't a number and end text isn't empty: Try getting index of provided text.
        if(end == -1 && !ranges[1].isEmpty())
            end = text.toLowerCase(Locale.ROOT).indexOf(ranges[1].toLowerCase(Locale.ROOT));
        
        return subString(raw, text, start, end);
    }
    
    private String subString(String raw, String text, int start, int end){
        if(start < 0)
            start = 0; // Make sure start isn't negative.
        
        if(end < 0 || end > text.length())
            end = text.length(); // Make sure end isn't negative nor larger than the string itself.
        
        if(start > (text.length() - 1) || end <= start){
            CachedWarnHelper.warn(expansion, raw, "Start index was either bigger than text length or bigger than end index.");
            return null; // Invalid range (Start is larger than text length or end is less than start)
        }
        
        return text.substring(start, end);
    }
}
