package com.andre601.formatterexpansion.formatters.number;

import com.andre601.formatterexpansion.FormatterExpansion;
import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.NumberUtils;
import com.andre601.formatterexpansion.utils.StringUtils;
import com.andre601.formatterexpansion.utils.logging.CachedWarnHelper;

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
    public String parse(String raw, String option, String... values){
        if(values.length < 2){
            CachedWarnHelper.warn(expansion, "too-short", raw, "Placeholder to short. Requires from:<value>, to:<value> and <number>");
            return null;
        }
        
        if(StringUtils.isNullOrEmpty(values[0], values[1])){
            CachedWarnHelper.warn(expansion, "null-values", raw, "Either to:<value> or <number> was empty/null.");
            return null;
        }
        
        if(!values[0].toLowerCase(Locale.ROOT).startsWith("to:")){
            CachedWarnHelper.warn(
                expansion,
                "no-to",
                raw,
                "Placeholder does not have to:<value> set."
            );
            return null;
        }
        
        String from = StringUtils.getSplit(option, ":", 2)[1];
        String to = StringUtils.getSplit(values[0], ":", 2)[1];
        
        if(StringUtils.isNullOrEmpty(from, to)){
            CachedWarnHelper.warn(
                expansion,
                "from-to-null",
                raw,
                "from:<value> and/or to:<value> had an empty/null <value>."
            );
            return null;
        }
    
        return convert(raw, StringUtils.merge(1, "", values), from, to);
    }
    
    private String convert(String raw, String number, String from, String to){
        Long finalNumber = NumberUtils.optLong(number);
        if(finalNumber == null){
            CachedWarnHelper.warn(
                expansion,
                "from-to-long",
                raw,
                "Cannot convert " + number + " into a long."
            );
            return null;
        }
        
        TimeUnit fromUnit = StringUtils.getTimeUnit(from);
        TimeUnit toUnit = StringUtils.getTimeUnit(to);
        
        if(fromUnit == null || toUnit == null){
            CachedWarnHelper.warn(
                expansion,
                "from-to-invalid-timeunit",
                raw,
                "Unsupported time unit for either from:<value> or to:<value>."
            );
            return null;
        }
        
        finalNumber = toUnit.convert(finalNumber, fromUnit);
    
        return switch(toUnit){
            case DAYS -> finalNumber + expansion.getString("time.days", "d");
            case HOURS -> finalNumber + expansion.getString("time.hours", "h");
            case MINUTES -> finalNumber + expansion.getString("time.minutes", "m");
            case SECONDS -> finalNumber + expansion.getString("time.seconds", "s");
            case MILLISECONDS -> finalNumber + expansion.getString("time.milliseconds", "ms");
            default -> {
                CachedWarnHelper.warn(expansion, "unknown-to-time", raw, "Unknown target time unit '" + to + "'.");
                yield null;
            }
        };
    }
}
