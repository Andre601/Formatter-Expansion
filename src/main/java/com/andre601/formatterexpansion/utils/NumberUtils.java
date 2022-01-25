package com.andre601.formatterexpansion.utils;

import com.andre601.formatterexpansion.FormatterExpansion;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class NumberUtils{
    
    private final FormatterExpansion expansion;
    
    public NumberUtils(FormatterExpansion expansion){
        this.expansion = expansion;
    }
    
    public String formatTime(String number, String unit){
        Long finalNumber = getNumber(number);
        if(finalNumber == null)
            return null;
        
        TimeUnit timeUnit = getTimeUnit(unit);
        if(timeUnit == null)
            return null;
        
        long days = 0, hours = 0, minutes = 0, seconds = 0, milliseconds = 0;
        
        final StringJoiner joiner = new StringJoiner(expansion.isCondensed() ? "" : " ");
        
        switch(timeUnit){
            case HOURS:
                days = timeUnit.toDays(finalNumber);
                hours = timeUnit.toHours(finalNumber) - (days * 24);
                
                break;
            
            case MINUTES:
                days = timeUnit.toDays(finalNumber);
                hours = timeUnit.toHours(finalNumber) - (days * 24);
                minutes = timeUnit.toMinutes(finalNumber) - (hours * 60) - (days * 1440);
                
                break;
            
            case SECONDS:
                days = timeUnit.toDays(finalNumber);
                hours = timeUnit.toHours(finalNumber) - (days * 24);
                minutes = timeUnit.toMinutes(finalNumber) - (hours * 60) - (days * 1440);
                seconds = timeUnit.toSeconds(finalNumber) - (minutes * 60) - (hours * 3600) - (days * 86400);
                
                break;
            
            case MILLISECONDS:
                days = timeUnit.toDays(finalNumber);
                hours = timeUnit.toHours(finalNumber) - (days * 24);
                minutes = timeUnit.toMinutes(finalNumber) - (hours * 60) - (days * 1440);
                seconds = timeUnit.toSeconds(finalNumber) - (minutes * 60) - (hours * 3600) - (days * 86400);
                milliseconds = finalNumber - (seconds * 1000) - (minutes * 60000) - (hours * 3600000) - (days * 86400000);
                
                break;
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
    
    public String convert(String number, String from, String to){
        Long finalNumber = getNumber(number);
        if(finalNumber == null)
            return null;
        
        TimeUnit fromUnit = getTimeUnit(from);
        TimeUnit toUnit = getTimeUnit(to);
        
        if(fromUnit == null || toUnit == null)
            return null;
        
        finalNumber = toUnit.convert(finalNumber, fromUnit);
        
        switch(toUnit){
            case DAYS:
                return finalNumber + expansion.getString("time.days", "d");
            
            case HOURS:
                return finalNumber + expansion.getString("time.hours", "h");
            
            case MINUTES:
                return finalNumber + expansion.getString("time.minutes", "m");
            
            case SECONDS:
                return finalNumber + expansion.getString("time.seconds", "s");
            
            case MILLISECONDS:
                return finalNumber + expansion.getString("time.milliseconds", "ms");
            
            default:
                return String.valueOf(finalNumber);
        }
    }
    
    public String formatNumber(String number){
        return formatNumber(number, expansion.getString("locale", "en-US"), expansion.getString("format", "#,###,###.##"));
    }
    
    public String formatNumber(String number, String locale, String format){
        // Allow arbitrary numbers
        BigDecimal decimal = getBigDecimal(number);
        if(decimal == null)
            return null;
        
        Locale loc = getLocale(locale);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(loc);
        DecimalFormat decimalFormat = (DecimalFormat)numberFormat;
        
        decimalFormat.applyPattern(format);
        
        return decimalFormat.format(decimal);
    }
    
    public String roundNumber(String number){
        return roundNumber(number, expansion.getInt("rounding.precision", 0), expansion.getString("rounding.mode", "half-up"));
    }
    
    public String roundNumber(String number, int precision, String roundingMode){
        // Allow arbitrary numbers
        BigDecimal decimal = getBigDecimal(number);
        if(decimal == null)
            return null;
        
        if(precision < 0)
            precision = 0; // Making sure precision isn't negative
        
        RoundingMode mode = getRoundingMode(roundingMode);
        MathContext context = new MathContext(precision, mode);
        
        return decimal.round(context).toPlainString();
    }
    
    private Locale getLocale(String input){
        if(input.contains("-")){
            String[] args = expansion.getSplit(input, "-", 2);
            if(!expansion.isNullOrEmpty(args[0], args[1])){
                return new Locale(args[0], args[1]);
            }else
            if(!expansion.isNullOrEmpty(args[0])){
                return new Locale(args[0]);
            }else{
                return Locale.US;
            }
        }else{
            return new Locale(input);
        }
    }
    
    private Long getNumber(String number){
        try{
            return Long.parseLong(number);
        }catch(NumberFormatException ex){
            return null;
        }
    }
    
    private BigDecimal getBigDecimal(String number){
        try{
            return new BigDecimal(number);
        }catch(NumberFormatException ex){
            return null;
        }
    }
    
    private RoundingMode getRoundingMode(String roundingMode){
        switch(roundingMode.toUpperCase(Locale.ROOT)){
            case "up":
                return RoundingMode.UP;
            
            case "down":
                return RoundingMode.DOWN;
            
            case "ceiling":
                return RoundingMode.CEILING;
            
            case "floor":
                return RoundingMode.FLOOR;
            
            case "half-down":
                return RoundingMode.HALF_DOWN;
            
            case "half-even":
                return RoundingMode.HALF_EVEN;
            
            case "half-up":
            default:
                return RoundingMode.HALF_UP;
        }
    }
    
    private TimeUnit getTimeUnit(String value){
        switch(value.toLowerCase(Locale.ROOT)){
            case "days":
            case "day":
                return TimeUnit.DAYS;
            
            case "fromhours":
            case "fromhrs":
            
            case "hours":
            case "hour":
            case "hrs":
                return TimeUnit.HOURS;
            
            case "fromminutes":
            case "frommins":
            
            case "minutes":
            case "minute":
            case "mins":
            case "min":
                return TimeUnit.MINUTES;
            
            case "fromseconds":
            case "fromsecs":
            
            case "seconds":
            case "second":
            case "secs":
            case "sec":
                return TimeUnit.SECONDS;
            
            case "frommilliseconds":
            case "fromms":
            
            case "milliseconds":
            case "millisecond":
            case "millis":
            case "ms":
                return TimeUnit.MILLISECONDS;
            
            default:
                return null;
        }
    }
}
