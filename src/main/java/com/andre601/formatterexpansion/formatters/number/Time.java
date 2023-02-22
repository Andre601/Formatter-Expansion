package com.andre601.formatterexpansion.formatters.number;

import com.andre601.formatterexpansion.FormatterExpansion;
import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.NumberUtils;
import com.andre601.formatterexpansion.utils.StringUtils;
import com.andre601.formatterexpansion.utils.logging.CachedWarnHelper;

import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class Time implements IFormatter{
    
    private final FormatterExpansion expansion;
    
    public Time(FormatterExpansion expansion){
        this.expansion = expansion;
    }
    
    @Override
    public String name(){
        return "time";
    }
    
    @Override
    public String parse(String raw, String option, String... values){
        if(values.length == 1 || !StringUtils.isNullOrEmpty(values[0]))
            return formatTime(raw, values[0], "fromSeconds");
        
        return formatTime(raw, StringUtils.merge(1, "", values), values[0]);
    }
    
    private String formatTime(String raw, String number, String unit){
        Long finalNumber = NumberUtils.optLong(number);
        if(finalNumber == null || finalNumber < 0L){
            CachedWarnHelper.warn(expansion, "long", raw, "Cannot convert " + number + " to a long.");
            return null;
        }
        
        TimeUnit timeUnit = StringUtils.getTimeUnit(unit);
        if(timeUnit == null){
            CachedWarnHelper.warn(expansion, raw, "Unsupported time unit '" + unit + "'.");
            return null;
        }
        
        long days = 0, hours = 0, minutes = 0, seconds = 0, milliseconds = 0;
        
        final StringJoiner joiner = new StringJoiner(expansion.isCondensed() ? "" : " ");
    
        switch(timeUnit){
            case HOURS -> {
                days = timeUnit.toDays(finalNumber);
                hours = timeUnit.toHours(finalNumber) - (days * 24);
            }
            case MINUTES -> {
                days = timeUnit.toDays(finalNumber);
                hours = timeUnit.toHours(finalNumber) - (days * 24);
                minutes = timeUnit.toMinutes(finalNumber) - (hours * 60) - (days * 1440);
            }
            case SECONDS -> {
                days = timeUnit.toDays(finalNumber);
                hours = timeUnit.toHours(finalNumber) - (days * 24);
                minutes = timeUnit.toMinutes(finalNumber) - (hours * 60) - (days * 1440);
                seconds = timeUnit.toSeconds(finalNumber) - (minutes * 60) - (hours * 3600) - (days * 86400);
            }
            case MILLISECONDS -> {
                days = timeUnit.toDays(finalNumber);
                hours = timeUnit.toHours(finalNumber) - (days * 24);
                minutes = timeUnit.toMinutes(finalNumber) - (hours * 60) - (days * 1440);
                seconds = timeUnit.toSeconds(finalNumber) - (minutes * 60) - (hours * 3600) - (days * 86400);
                milliseconds = finalNumber - (seconds * 1000) - (minutes * 60000) - (hours * 3600000) - (days * 86400000);
            }
        }
        
        if(days > 0)
            joiner.add(days + expansion.getString("time.days", "d"));
        
        if(hours > 0)
            joiner.add(hours + expansion.getString("time.hours", "h"));
        
        if(minutes > 0)
            joiner.add(minutes + expansion.getString("time.minutes", "m"));
        
        if(seconds > 0)
            joiner.add(seconds + expansion.getString("time.seconds", "s"));
        
        if(milliseconds > 0)
            joiner.add(milliseconds + expansion.getString("time.milliseconds", "ms"));
        
        return joiner.toString();
    }
}
