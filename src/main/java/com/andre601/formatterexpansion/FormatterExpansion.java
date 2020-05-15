package com.andre601.formatterexpansion;

import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatterExpansion extends PlaceholderExpansion implements Configurable{
    private final Pattern time;
    private final Pattern formatPattern;
    private final Pattern numberPattern;
    private final Pattern localePattern;
    
    public FormatterExpansion(){
        time          = Pattern.compile("(?:_)?time(?:_)?", Pattern.CASE_INSENSITIVE);
        formatPattern = Pattern.compile("(?:_)?format:\\((?<format>.+)\\)(?:_)?", Pattern.CASE_INSENSITIVE);
        numberPattern = Pattern.compile("(?:_)?value:\\((?<number>\\d+)\\)(?:_)?", Pattern.CASE_INSENSITIVE);
        localePattern = Pattern.compile("(?:_)?locale:\\((?<locale>.+)\\)(?:_)?", Pattern.CASE_INSENSITIVE);
    }
    
    @Override
    public String getIdentifier(){
        return "formatter";
    }
    
    @Override
    public String getAuthor(){
        return "Andre_601";
    }
    
    @Override
    public String getVersion(){
        return "{VERSION}";
    }
    
    @Override
    public Map<String, Object> getDefaults(){
        Map<String, Object> defaults = new HashMap<>();
        
        defaults.put("format", "#,###,###.##");
        defaults.put("locale", "en_US");
        
        defaults.put("time.seconds", "s");
        defaults.put("time.minutes", "m");
        defaults.put("time.hours", "h");
        defaults.put("time.days", "d");
        defaults.put("time.condensed", "no");
        
        return defaults;
    }
    
    private Locale getLocale(String input){
        if(input.contains("_")){
            String[] args = input.split("_");
            if(args.length >= 2){
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
            return String.valueOf(seconds) + this.getString("time.seconds", "s");
        
        final StringBuilder builder = new StringBuilder();
        
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        
        seconds %= 60;
        minutes %= 60;
        hours %= 60;
        days %= 24;
        
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
    
    @Override
    public String onRequest(OfflinePlayer player, String identifier){
        identifier = PlaceholderAPI.setBracketPlaceholders(player, identifier);
        
        Matcher numberMatcher = numberPattern.matcher(identifier);
        if(!numberMatcher.find())
            return "No number or placeholder provided!";
    
        long number;
        try{
            number = Long.parseLong(numberMatcher.group("number"));
        }catch(NumberFormatException ex){
            return "Could not parse number!";
        }
        
        Matcher timeMatcher = time.matcher(identifier);
        if(timeMatcher.find()){
            return formatTime(number);
        }
        
        Locale locale;
        Matcher localeMatcher = localePattern.matcher(identifier);
        if(localeMatcher.find()){
            locale = getLocale(localeMatcher.group("locale"));
        }else{
            locale = getLocale(this.getString("locale", "en_US"));
        }
        
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        DecimalFormat decimalFormat = (DecimalFormat)numberFormat;
        
        
        Matcher formatMatcher = formatPattern.matcher(identifier);
        if(formatMatcher.find()){
            decimalFormat.applyPattern(formatMatcher.group("format"));
        }else{
            decimalFormat.applyPattern(this.getString("format", "#,###,###.##"));
        }
        
        return decimalFormat.format(number);
    }
}
