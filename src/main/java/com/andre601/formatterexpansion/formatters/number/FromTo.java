package com.andre601.formatterexpansion.formatters.number;

import com.andre601.formatterexpansion.FormatterExpansion;
import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.NumberUtils;
import com.andre601.formatterexpansion.utils.StringUtils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FromTo implements IFormatter{
    
    private final FormatterExpansion expansion;
    
    public FromTo(FormatterExpansion expansion){
        this.expansion = expansion;
    }
    
    @Override
    public String name(){
        return null; // Never used for this special case.
    }
    
    @Override
    public String parse(String option, String... values){
        if(values.length < 2 || !StringUtils.isNullOrEmpty(values[0], values[1]) || !values[0].toLowerCase(Locale.ROOT).startsWith("to:"))
            return null;
        
        String from = StringUtils.getSplit(option, ":", 2)[1];
        String to = StringUtils.getSplit(values[0], ":", 2)[1];
        
        if(StringUtils.isNullOrEmpty(from, to))
            return null;
    
        return convert(StringUtils.merge(1, "", values), from, to);
    }
    
    private String convert(String number, String from, String to){
        Long finalNumber = NumberUtils.optLong(number);
        if(finalNumber == null)
            return null;
        
        TimeUnit fromUnit = StringUtils.getTimeUnit(from);
        TimeUnit toUnit = StringUtils.getTimeUnit(to);
        
        if(fromUnit == null || toUnit == null)
            return null;
        
        finalNumber = toUnit.convert(finalNumber, fromUnit);
    
        return switch(toUnit){
            case DAYS -> finalNumber + expansion.getString("time.days", "d");
            case HOURS -> finalNumber + expansion.getString("time.hours", "h");
            case MINUTES -> finalNumber + expansion.getString("time.minutes", "m");
            case SECONDS -> finalNumber + expansion.getString("time.seconds", "s");
            case MILLISECONDS -> finalNumber + expansion.getString("time.milliseconds", "ms");
            default -> null;
        };
    }
}
