package com.andre601.formatterexpansion;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nonnull;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatterExpansion extends PlaceholderExpansion implements Configurable{
    private final Pattern formatPattern;
    private final Pattern localePattern;
    
    public FormatterExpansion(){
        formatPattern = Pattern.compile("format:(?<format>.+)", Pattern.CASE_INSENSITIVE);
        localePattern = Pattern.compile("locale:(?<locale>.+)", Pattern.CASE_INSENSITIVE);
    }
    
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
        defaults.put("locale", "en:US");
        
        defaults.put("time.seconds", "s");
        defaults.put("time.minutes", "m");
        defaults.put("time.hours", "h");
        defaults.put("time.days", "d");
        defaults.put("time.condensed", "no");
        
        return defaults;
    }
    
    private Locale getLocale(String input){
        if(input.contains(":")){
            String[] args = Arrays.copyOf(input.split(":", 2), 2);
            if(args[0] != null && args[1] != null){
                return new Locale(args[0], args[1]);
            }else{
                return Locale.US;
            }
        }else{
            return new Locale(input);
        }
    }
    
    /* 
     * From the Statistic expansion
     * https://github.com/PlaceholderAPI/Statistics-Expansion
     */
    private String formatTime(long seconds){
        if(seconds <= 0)
            return seconds + this.getString("time.seconds", "s");
        
        final StringBuilder builder = new StringBuilder();
        
        long days = TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - days * 24;
        long minutes = TimeUnit.SECONDS.toMinutes(seconds) - hours * 60 - days * 1440;
        seconds = seconds - minutes * 60 - hours * 3600 - days * 86400;
        
        if(days > 0){
            builder.append(days)
                   .append(this.getString("time.days", "d"));
        }
        
        if(hours > 0){
            if((builder.length() > 0) && (this.getString("time.condensed", "no").equalsIgnoreCase("no")))
                builder.append(" ");
            
            builder.append(hours)
                   .append(this.getString("time.hours", "h"));
        }
        
        if(minutes > 0){
            if((builder.length() > 0) && (this.getString("time.condensed", "no").equalsIgnoreCase("no")))
                builder.append(" ");
    
            builder.append(minutes)
                   .append(this.getString("time.minutes", "m"));
        }
        
        if(seconds > 0){
            if((builder.length() > 0) && (this.getString("time.condensed", "no").equalsIgnoreCase("no")))
                builder.append(" ");
    
            builder.append(seconds)
                   .append(this.getString("time.seconds", "s"));
        }
        
        return builder.toString();
    }
    
    private String formatNumber(long number){
        return formatNumber(number, this.getString("format", "#,###,###.##"), this.getString("locale", "en:US"));
    }
    
    private String formatNumber(long number, String value, Type type){
        if(type.equals(Type.FORMAT))
            return formatNumber(number, value, this.getString("locale", "en:US"));
        else
        if(type.equals(Type.LOCALE))
            return formatNumber(number, this.getString("format", "#,###,###.##"), value);
        else
            return formatNumber(number);
    }
    
    private String formatNumber(long number, String format, String locale){
        Locale loc = getLocale(locale);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(loc);
        DecimalFormat decimalFormat = (DecimalFormat)numberFormat;
        
        decimalFormat.applyPattern(format);
        
        return decimalFormat.format(number);
    }
    
    private String[] getSplit(String text, String split, int length){
        return Arrays.copyOf(text.split(split, length), length);
    }
    
    @Override
    public String onRequest(OfflinePlayer player, @Nonnull String identifier){
        identifier = PlaceholderAPI.setBracketPlaceholders(player, identifier);
        String[] values = getSplit(identifier, "_", 4);
        
        if(values[0] == null)
            return null;
        
        if(values[0].equalsIgnoreCase("text")){
            if(values[1] == null)
                return null;
            
            switch(values[1].toLowerCase()){
                case "substring":
                    if(values[3] == null)
                        return null;
                    
                    String[] options = getSplit(values[2], ":", 2);
                    int start;
                    int end;
                    
                    if(options[0] == null){
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
                    
                    if(options[1] == null){
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
                    if(values[2] == null)
                        return null;
                    
                    if(values[3] != null)
                        values[2] = String.join("_", values[2], values[3]);
                    
                    return values[2].toUpperCase();
                
                case "lowercase":
                    if(values[2] == null)
                        return null;
    
                    if(values[3] != null)
                        values[2] = String.join("_", values[2], values[3]);
                    
                    return values[2].toLowerCase();
            }
        }else
        if(values[0].equalsIgnoreCase("number")){
            if(values[1] == null)
                return null;
            
            switch(values[1].toLowerCase()){
                case "format":
                    long number;
                    try{
                        number = Long.parseLong(values[2]);
                    }catch(NumberFormatException ex){
                        return null;
                    }
                    
                    if(values[3] == null){
                        return formatNumber(number);
                    }
                    
                    String[] options = getSplit(values[3], "_", 2);
                    if(options[0] == null)
                        return null;
                    
                    Matcher localeMatcher = localePattern.matcher(options[0]);
                    Matcher formatMatcher = formatPattern.matcher(options[0]);
                    
                    if(localeMatcher.matches()){
                        if(options[1] == null)
                            return formatNumber(number, localeMatcher.group("locale"), Type.LOCALE);
                        
                        formatMatcher = formatPattern.matcher(options[1]);
                        
                        if(formatMatcher.matches())
                            return formatNumber(number, formatMatcher.group("format"), localeMatcher.group("locale"));
                        else
                            return formatNumber(number, localeMatcher.group("locale"), Type.LOCALE);
                    }else
                    if(formatMatcher.matches()){
                        if(options[1] == null)
                            return formatNumber(number, formatMatcher.group("format"), Type.FORMAT);
    
                        localeMatcher = localePattern.matcher(options[1]);
    
                        if(localeMatcher.matches())
                            return formatNumber(number, formatMatcher.group("format"), localeMatcher.group("locale"));
                        else
                            return formatNumber(number, formatMatcher.group("format"), Type.FORMAT);
                    }
                    break;
                
                case "time":
                    long time;
                    try{
                        time = Long.parseLong(values[2]);
                    }catch(NumberFormatException ex){
                        return null;
                    }
                    
                    return formatTime(time);
            }
        }
        
        return null;
    }
    
    private enum Type{
        FORMAT,
        LOCALE
    }
}
