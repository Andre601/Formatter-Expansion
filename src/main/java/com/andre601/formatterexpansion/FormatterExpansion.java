package com.andre601.formatterexpansion;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class FormatterExpansion extends PlaceholderExpansion implements Configurable{
    
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
        String[] values = getSplit(identifier, "_", 5);
        
        if(values[0] == null)
            return null;
        
        if(values[0].equalsIgnoreCase("text")){
            if(isNullOrEmpty(values[1]))
                return null;
            
            switch(values[1].toLowerCase()){
                case "substring":
                    if(isNullOrEmpty(values[3]))
                        return null;
                    
                    String[] options = getSplit(values[2], ":", 2);
                    int start;
                    int end;
                    
                    if(isNullOrEmpty(options[0])){
                        start = 0;
                    }else{
                        try{
                            start = Integer.parseInt(options[0]);
                        }catch(NumberFormatException ex){
                            start = 0;
                        }
                        
                        if(start < 0)
                            start = 0;
                    }
                    
                    if(isNullOrEmpty(options[1])){
                        end = values[3].length();
                    }else{
                        try{
                            end = Integer.parseInt(options[1]);
                        }catch(NumberFormatException ex){
                            end = values[3].length();
                        }
                        
                        if(end == -1 || end > values[3].length())
                            end = values[3].length();
                    }
                    
                    if(start > end)
                        return null;
                    
                    return values[3].substring(start, end);
                
                case "uppercase":
                    if(isNullOrEmpty(values[2]))
                        return null;
                    
                    if(values[3] != null)
                        values[2] = String.join("_", values[2], values[3]);
                    
                    if(values[4] != null)
                        values[2] = String.join("_", values[2], values[4]);
                    
                    return values[2].toUpperCase();
                
                case "lowercase":
                    if(isNullOrEmpty(values[2]))
                        return null;
    
                    if(values[3] != null)
                        values[2] = String.join("_", values[2], values[3]);
    
                    if(values[4] != null)
                        values[2] = String.join("_", values[2], values[4]);
                    
                    return values[2].toLowerCase();
                
                case "join":
                    if(isNullOrEmpty(values[2], values[3], values[4]))
                        return null;
                    
                    String target = values[2];
                    String replacement = values[3];
                    
                    String[] splits = values[4].split(Pattern.quote(target));
                    
                    return String.join(replacement, splits);
            }
        }else
        if(values[0].equalsIgnoreCase("number")){
            if(isNullOrEmpty(values[1]))
                return null;
            
            switch(values[1].toLowerCase()){
                case "format":
                    if(values[3] == null){
                        long number;
                        try{
                            number = Long.parseLong(values[2]);
                        }catch(NumberFormatException ex){
                            return null;
                        }
                        
                        return formatNumber(number);
                    }
                    
                    long number;
                    try{
                        number = Long.parseLong(values[3]);
                    }catch(NumberFormatException ex){
                        return null;
                    }
                    
                    if(isNullOrEmpty(values[2])){
                        return formatNumber(number);
                    }
                    
                    String[] options = getSplit(values[2], ":", 2);
                    
                    String locale = isNullOrEmpty(options[0]) ? this.getString("locale", "en-US") : options[0];
                    String format = isNullOrEmpty(options[1]) ? this.getString("format", "#,###,###.##") : options[1];
                    
                    return formatNumber(number, format, locale);
                
                case "time":
                    if(isNullOrEmpty(values[3])){
                        long time;
                        try{
                            time = Long.parseLong(values[2]);
                        }catch(NumberFormatException ex){
                            return null;
                        }
    
                        return formatTime(time, TimeUnit.SECONDS);
                    }
                    
                    long num;
                    try{
                        num = Long.parseLong(values[3]);
                    }catch(NumberFormatException ex){
                        return null;
                    }
                    
                    switch(values[2].toLowerCase()){
                        case "secs":
                        case "seconds":
                            return formatTime(num, TimeUnit.SECONDS);
                        
                        case "mins":
                        case "minutes":
                            return formatTime(num, TimeUnit.MINUTES);
                        
                        case "hrs":
                        case "hours":
                            return formatTime(num, TimeUnit.HOURS);
                        
                        default:
                            return null;
                    }
            }
        }
        
        return null;
    }
    
    private boolean isNullOrEmpty(String... strings){
        for(String s : strings){
            if(s == null || s.isEmpty())
                return true;
        }
        
        return false;
    }
    
    private String formatTime(long number, TimeUnit timeUnit){
        
        long days;
        long hours;
        long minutes;
        long seconds;
        
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
        }
    }
    
    private String formatNumber(long number){
        return formatNumber(number, this.getString("format", "#,###,###.##"), this.getString("locale", "en-US"));
    }
    
    private String formatNumber(long number, String format, String locale){
        Locale loc = getLocale(locale);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(loc);
        DecimalFormat decimalFormat = (DecimalFormat)numberFormat;
        
        decimalFormat.applyPattern(format);
        
        return decimalFormat.format(number);
    }
    
    private Locale getLocale(String input){
        if(input.contains("-")){
            String[] args = Arrays.copyOf(input.split("-", 2), 2);
            if(args[0] != null && args[1] != null){
                return new Locale(args[0], args[1]);
            }else
            if(args[0] != null){
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
    
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    private boolean isCondensed(){
        Object condensed = this.get("time.condensed", null);
        
        if(condensed == null)
            return false;
        
        if(condensed instanceof String)
            return this.getString("time.condensed", "no").equalsIgnoreCase("yes");
        
        if(condensed instanceof Boolean){
            ConfigurationSection section = this.getConfigSection();
            if(section == null)
                return true;
            
            return section.getBoolean("time.condensed", false);
        }
        
        return true;
    }
}
