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
    private final Pattern formatPattern = Pattern.compile("(_)?format:\\((?<format>.+)\\)(_)?", Pattern.CASE_INSENSITIVE);
    private final Pattern numberPattern = Pattern.compile("(_)?value:\\((?<number>\\d+)\\)(_)?", Pattern.CASE_INSENSITIVE);
    private final Pattern localePattern = Pattern.compile("(_)?locale:\\((?<locale>.+)\\)(_)?", Pattern.CASE_INSENSITIVE);
    
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
    
    @Override
    public String onRequest(OfflinePlayer player, String identifier){
        identifier = PlaceholderAPI.setBracketPlaceholders(player, identifier);
        
        Matcher numberMatcher = numberPattern.matcher(identifier);
        if(!numberMatcher.find())
            return "No number or placeholder provided!";
    
        Locale locale;
        Matcher localeMatcher = localePattern.matcher(identifier);
        if(localeMatcher.find()){
            locale = getLocale(localeMatcher.group("locale"));
        }else{
            locale = getLocale(this.getString("locale", "en_US"));
        }
        
        NumberFormat numberFormat = NumberFormat.getNumberInstance(locale);
        DecimalFormat decimalFormat = (DecimalFormat)numberFormat;
        
        long number;
        try{
            number = Long.parseLong(numberMatcher.group("number"));
        }catch(NumberFormatException ex){
            return "Could not parse number!";
        }
        
        Matcher formatMatcher = formatPattern.matcher(identifier);
        if(formatMatcher.find()){
            decimalFormat.applyPattern(formatMatcher.group("format"));
        }else{
            decimalFormat.applyPattern(this.getString("format", "#,###,###.##"));
        }
        
        return decimalFormat.format(number);
    }
}
