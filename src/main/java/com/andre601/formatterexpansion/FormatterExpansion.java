package com.andre601.formatterexpansion;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderAPIPlugin;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nonnull;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class FormatterExpansion extends PlaceholderExpansion implements Configurable{
    
    private final Logger logger = PlaceholderAPIPlugin.getInstance().getLogger();
    
    @Override
    @Nonnull
    public String getIdentifier(){
        return "formatter";
    }
    
    @Override
    @Nonnull
    public String getAuthor(){
        return "Andre_601";
    }
    
    @Override
    @Nonnull
    public String getVersion(){
        return "VERSION";
    }
    
    @Override
    public Map<String, Object> getDefaults(){
        Map<String, Object> defaults = new HashMap<>();
        
        defaults.put("format", "#,###,###.##");
        defaults.put("locale", "en-US");
        
        defaults.put("time.milliseconds", "ms");
        defaults.put("time.seconds", "s");
        defaults.put("time.minutes", "m");
        defaults.put("time.hours", "h");
        defaults.put("time.days", "d");
        defaults.put("time.condensed", false);
        
        return defaults;
    }
    
    @Override
    public String onRequest(OfflinePlayer player, @Nonnull String identifier){
        identifier = PlaceholderAPI.setBracketPlaceholders(player, identifier);
        String[] temp = getSplit(identifier, "_", 3);
        
        if(isNullOrEmpty(temp[0], temp[1], temp[2]))
            return null;
    
        String type = temp[0];
        String option = temp[1];
        String value = temp[2];
        
        if(type.equalsIgnoreCase("text")){
            switch(option.toLowerCase(Locale.ROOT)){
                case "substring":
                    String[] split = getSplit(value, "_", 2);
                    
                    if(isNullOrEmpty(split[0], split[1]))
                        return null;
                    
                    String[] range = getSplit(split[0], ":", 2);
                    int start = 0;
                    int end = split[1].length();
                    
                    if(!isNullOrEmpty(range[0])){
                        try{
                            start = Integer.parseInt(range[0]);
                        }catch(NumberFormatException ignored){}
                        
                        if(start < 0)
                            start = 0;
                    }
                    
                    if(!isNullOrEmpty(range[1])){
                        try{
                            end = Integer.parseInt(range[1]);
                        }catch(NumberFormatException ignored){}
                        
                        if(end > split[1].length())
                            end = split[1].length();
                    }
                    
                    return split[1].substring(start, end);
                
                case "uppercase":
                    return value.toUpperCase(Locale.ROOT);
                
                case "lowercase":
                    return value.toLowerCase(Locale.ROOT);
                
                case "replace":
                    String[] replaceValues = getSplit(value, "_", 3);
                    if(isNullOrEmpty(replaceValues[0], replaceValues[2]) || replaceValues[1] == null)
                        return null;
                    
                    String target = replaceValues[0].replace("{{u}}", "_");
                    String replacement = replaceValues[1].replace("{{u}}", "_");
                    
                    return replaceValues[2].replace(target, replacement);
                
                case "length":
                    return String.valueOf(value.length());
                
                default:
                    return null;
            }
        }else
        if(type.equalsIgnoreCase("number") || temp[0].equalsIgnoreCase("num")){
            if(option.toLowerCase(Locale.ROOT).startsWith("from:")){
                if(!value.toLowerCase(Locale.ROOT).startsWith("to:"))
                    return null;
                
                // %formatter_num_from:<type>_to:<type>_<number>%
                
                String[] valueOptions = getSplit(value, "_", 2);
                
                String fromValue = getSplit(option, ":", 2)[1];
                String toValue = getSplit(valueOptions[0], ":", 2)[1];
                
                if(isNullOrEmpty(fromValue, toValue, valueOptions[1]))
                    return null;
                
                return convert(getTimeUnit(fromValue), getTimeUnit(toValue), valueOptions[1]);
            }
            
            switch(option.toLowerCase(Locale.ROOT)){
                case "format":
                    String[] formatValues = getSplit(value, "_", 2);
                    
                    // placeholder must be %formatter_num(ber)_format_<number>%
                    if(isNullOrEmpty(formatValues[1]))
                        return formatNumber(formatValues[0]);
                    
                    String[] options = getSplit(formatValues[0], ":", 2);
                    String locale = isNullOrEmpty(options[0]) ? this.getString("locale", "en-US") : options[0];
                    String pattern = isNullOrEmpty(options[1]) ? this.getString("format", "#,###,###.##") : options[1];
                    
                    return formatNumber(formatValues[1], locale, pattern);
                
                case "time":
                    String[] timeValues = getSplit(value, "_", 2);
                    
                    // placeholder must be %formatter_num(ber)_time_<number>%
                    if(isNullOrEmpty(timeValues[1]))
                        return formatTime(timeValues[0], TimeUnit.SECONDS);
                    
                    switch(timeValues[0].toLowerCase(Locale.ROOT)){
                        case "frommilliseconds":
                        case "fromms":
                            return formatTime(timeValues[1], TimeUnit.MILLISECONDS);
                        
                        case "fromseconds":
                        case "fromsecs":
                            return formatTime(timeValues[1], TimeUnit.SECONDS);
                        
                        case "fromminutes":
                        case "frommins":
                            return formatTime(timeValues[1], TimeUnit.MINUTES);
                        
                        case "fromhours":
                        case "fromhrs":
                            return formatTime(timeValues[1], TimeUnit.HOURS);
                        
                        default:
                            return null;
                    }
            }
        }
        
        return null;
    }
    
    // Convenience method to check if any provided String is either null, or empty
    private boolean isNullOrEmpty(String... strings){
        for(String s : strings){
            if(s == null || s.isEmpty())
                return true;
        }
        
        return false;
    }
    
    private String formatTime(String num, TimeUnit timeUnit){
        long number;
        try{
            number = Long.parseLong(num);
        }catch(NumberFormatException ex){
            return null;
        }
        
        long days;
        long hours;
        long minutes;
        long seconds;
        long milliseconds;
        
        final StringBuilder builder = new StringBuilder();
        
        switch(timeUnit){
            case HOURS:
                days = timeUnit.toDays(number);
                hours = timeUnit.toHours(number) - days * 24;
                
                if(days > 0){
                    builder.append(days)
                           .append(this.getString("time.days", "d"));
                }
                
                if(hours > 0){
                    if((builder.length() > 0) && !isCondensed()){
                        builder.append(" ");
                    }
                    
                    builder.append(hours)
                           .append(this.getString("time.hours", "h"));
                }
                
                return builder.toString();
            
            case MINUTES:
                days = timeUnit.toDays(number);
                hours = timeUnit.toHours(number) - days * 24;
                minutes = timeUnit.toMinutes(number) - hours * 60 - days * 1440;
                
                if(days > 0){
                    builder.append(days)
                           .append(this.getString("time.days", "d"));
                }
    
                if(hours > 0){
                    if((builder.length() > 0) && !isCondensed()){
                        builder.append(" ");
                    }
                    
                    builder.append(hours)
                           .append(this.getString("time.hours", "h"));
                }
    
                if(minutes > 0){
                    if((builder.length() > 0) && !isCondensed()){
                        builder.append(" ");
                    }
                    
                    builder.append(minutes)
                           .append(this.getString("time.minutes", "m"));
                }
                
                return builder.toString();
            
            case SECONDS:
            default:
                days = timeUnit.toDays(number);
                hours = timeUnit.toHours(number) - days * 24;
                minutes = timeUnit.toMinutes(number) - hours * 60 - days * 1440;
                seconds = number - minutes * 60 - hours * 3600 - days * 86400;
    
                if(days > 0){
                    builder.append(days)
                           .append(this.getString("time.days", "d"));
                }
    
                if(hours > 0){
                    if((builder.length() > 0) && !isCondensed()){
                        builder.append(" ");
                    }
        
                    builder.append(hours)
                           .append(this.getString("time.hours", "h"));
                }
    
                if(minutes > 0){
                    if((builder.length() > 0) && !isCondensed()){
                        builder.append(" ");
                    }
        
                    builder.append(minutes)
                           .append(this.getString("time.minutes", "m"));
                }
                
                if(seconds > 0){
                    if((builder.length() > 0) && !isCondensed()){
                        builder.append(" ");
                    }
                    
                    builder.append(seconds)
                           .append(this.getString("time.seconds", "s"));
                }
    
                return builder.toString();
            
            case MILLISECONDS:
                days = timeUnit.toDays(number);
                hours = timeUnit.toHours(number) - days * 24;
                minutes = timeUnit.toMinutes(number) - hours * 60 - days * 1440;
                seconds = timeUnit.toSeconds(number) - minutes * 60 - hours * 3600 - days * 86400;
                milliseconds = number - seconds * 1000 - minutes * 60000 - hours * 3600000 - days * 86400000;
                
                if(days > 0){
                    builder.append(days)
                           .append(this.getString("time.days", "d"));
                }
    
                if(hours > 0){
                    if((builder.length() > 0) && !isCondensed()){
                        builder.append(" ");
                    }
        
                    builder.append(hours)
                           .append(this.getString("time.hours", "h"));
                }
    
                if(minutes > 0){
                    if((builder.length() > 0) && !isCondensed()){
                        builder.append(" ");
                    }
        
                    builder.append(minutes)
                           .append(this.getString("time.minutes", "m"));
                }
    
                if(seconds > 0){
                    if((builder.length() > 0) && !isCondensed()){
                        builder.append(" ");
                    }
        
                    builder.append(seconds)
                           .append(this.getString("time.seconds", "s"));
                }
                
                if(milliseconds > 0){
                    if((builder.length() > 0) && !isCondensed()){
                        builder.append(" ");
                    }
                    
                    builder.append(milliseconds)
                           .append(this.getString("time.milliseconds", "ms"));
                }
                
                return builder.toString();
        }
    }
    
    private String convert(TimeUnit from, TimeUnit to, String num){
        long number;
        try{
            number = Long.parseLong(num);
        }catch(NumberFormatException ignored){
            return null;
        }
        
        number = to.convert(number, from);
        StringBuilder builder = new StringBuilder().append(number);
        
        switch(to){
            case DAYS:
                builder.append(this.getString("time.days", "d"));
                break;
            
            case HOURS:
                builder.append(this.getString("time.hours", "h"));
                break;
            
            case MINUTES:
                builder.append(this.getString("time.minutes", "m"));
                break;
            
            case SECONDS:
            default:
                builder.append(this.getString("time.seconds", "s"));
                break;
            
            case MILLISECONDS:
                builder.append(this.getString("time.milliseconds", "ms"));
                break;
        }
        
        return builder.toString();
    }
    
    private TimeUnit getTimeUnit(String value){
        switch(value.toLowerCase(Locale.ROOT)){
            case "days":
            case "day":
                return TimeUnit.DAYS;
            
            case "hours":
            case "hour":
            case "hrs":
                return TimeUnit.HOURS;
            
            case "minutes":
            case "minute":
            case "mins":
            case "min":
                return TimeUnit.MINUTES;
            
            case "seconds":
            case "second":
            case "secs":
            case "sec":
            default:
                return TimeUnit.SECONDS;
            
            case "milliseconds":
            case "millisecond":
            case "millis":
            case "ms":
                return TimeUnit.MILLISECONDS;
        }
    }
    
    private String formatNumber(String num){
        return formatNumber(num, this.getString("locale", "en-US"), this.getString("format", "#,###,###.##"));
    }
    
    private String formatNumber(String num, String locale, String format){
        // Allow arbitrary numbers
        BigDecimal number;
        try{
            number = new BigDecimal(num);
        }catch(NumberFormatException ex){
            return null;
        }
        
        Locale loc = getLocale(locale);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(loc);
        DecimalFormat decimalFormat = (DecimalFormat)numberFormat;
        
        decimalFormat.applyPattern(format);
        
        return decimalFormat.format(number);
    }
    
    private Locale getLocale(String input){
        if(input.contains("-")){
            String[] args = getSplit(input, "-", 2);
            if(!isNullOrEmpty(args[0]) && !isNullOrEmpty(args[1])){
                return new Locale(args[0], args[1]);
            }else
            if(!isNullOrEmpty(args[0])){
                return new Locale(args[0]);
            }else{
                return Locale.US;
            }
        }else{
            return new Locale(input);
        }
    }
    
    private String[] getSplit(String text, String split, int length){
        return Arrays.copyOf(text.split(split, length), length);
    }
    
    /*
     * We have this method for backwards-compatability with configurations from before 1.3.2
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isCondensed(){
        Object condensed = this.get("time.condensed", null);
        
        if(condensed == null)
            return false;
        
        if(condensed instanceof String)
            return this.getString("time.condensed", "no").equalsIgnoreCase("yes");
        
        if(condensed instanceof Boolean)
            return this.getBoolean("time.condensed", false);
        
        return true;
    }
    
    private void logDeprecated(String name){
        String word = name.substring(0, 1).toUpperCase(Locale.ROOT) + name.substring(1).toLowerCase(Locale.ROOT);
        
        logger.warning(String.format(
                "[Formatter] Usage of %%formatter_number_time_%s_<number>%% detected!",
                name.toLowerCase(Locale.ROOT)
        ));
        logger.warning("[Formatter] This placeholder is DEPRECATED and will be removed in a future release.");
        logger.warning(String.format(
                "[Formatter] Please switch to %%formatter_number_time_from%s_<number>%% instead!",
                word
        ));
    }
}
