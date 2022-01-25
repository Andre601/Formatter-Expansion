package com.andre601.formatterexpansion;

import com.andre601.formatterexpansion.utils.NumberUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.Cacheable;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nonnull;
import java.util.*;

public class FormatterExpansion extends PlaceholderExpansion implements Configurable, Cacheable{
    
    private final Map<String, Object> defaults = new HashMap<>();
    private NumberUtils numberUtils = null;
    
    public FormatterExpansion(){
        defaults.put("format", "#,###,###.##");
        defaults.put("locale", "en-US");
        
        defaults.put("time.milliseconds", "ms");
        defaults.put("time.seconds", "s");
        defaults.put("time.minutes", "m");
        defaults.put("time.hours", "h");
        defaults.put("time.days", "d");
        defaults.put("time.condensed", false);
        
        defaults.put("rounding.precision", 0);
        defaults.put("rounding.mode", "half-up");
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
        return defaults;
    }
    
    @Override
    public void clear(){
        numberUtils = null;
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
                        }catch(NumberFormatException ignored){
                            // Don't modify the starting point
                        }
                        
                        if(start < 0)
                            start = 0;
                    }
                    
                    if(!isNullOrEmpty(range[1])){
                        try{
                            end = Integer.parseInt(range[1]);
                        }catch(NumberFormatException ignored){
                            // Don't modify the ending point
                        }
                        
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
        if(type.equalsIgnoreCase("number") || type.equalsIgnoreCase("num")){
            if(numberUtils == null)
                numberUtils = new NumberUtils(this);
            
            if(option.toLowerCase(Locale.ROOT).startsWith("from:")){
                if(!value.toLowerCase(Locale.ROOT).startsWith("to:"))
                    return null;
                
                // %formatter_num(ber)_from:<type>_to:<type>_<number>%
                
                String[] valueOptions = getSplit(value, "_", 2);
                
                String fromValue = getSplit(option, ":", 2)[1];
                String toValue = getSplit(valueOptions[0], ":", 2)[1];
                
                if(isNullOrEmpty(fromValue, toValue, valueOptions[1]))
                    return null;
                
                return numberUtils.convert(valueOptions[1], fromValue, toValue);
            }
            
            if(option.equalsIgnoreCase("rounding") || option.equalsIgnoreCase("round")){
                // Round with set rounding method in config.yml
                
                if(!value.toLowerCase(Locale.ROOT).contains("_")){
                    // %formatter_num(ber)_round(ing)_<number>%
                    return numberUtils.roundNumber(value);
                }
                
                // %formatter_num(ber)_round(ing)_precision:<mode>_value>%
                
                String[] roundValue = getSplit(value, "_", 2);
                String[] roundingOptions = getSplit(roundValue[0], ":", 2);
                
                int precision = this.getInt("rounding.precision", 0);
                String roundingMode = this.getString("rounding.mode", "half-up");
                
                if(!isNullOrEmpty(roundingOptions[0])){
                    try{
                        precision = Integer.parseInt(roundingOptions[0]);
                    }catch(NumberFormatException ignored){
                        // Don't modify the precision
                    }
                }
                
                if(!isNullOrEmpty(roundingOptions[1]))
                    roundingMode = roundingOptions[1];
                
                return numberUtils.roundNumber(value, precision, roundingMode);
            }
            
            switch(option.toLowerCase(Locale.ROOT)){
                case "format":
                    String[] formatValues = getSplit(value, "_", 2);
                    
                    // placeholder must be %formatter_num(ber)_format_<number>%
                    if(isNullOrEmpty(formatValues[1]))
                        return numberUtils.formatNumber(formatValues[0]);
                    
                    String[] options = getSplit(formatValues[0], ":", 2);
                    String locale = isNullOrEmpty(options[0]) ? this.getString("locale", "en-US") : options[0];
                    String pattern = isNullOrEmpty(options[1]) ? this.getString("format", "#,###,###.##") : options[1];
                    
                    return numberUtils.formatNumber(formatValues[1], locale, pattern);
                
                case "time":
                    String[] timeValues = getSplit(value, "_", 2);
                    
                    // placeholder must be %formatter_num(ber)_time_<number>%
                    if(isNullOrEmpty(timeValues[1]))
                        return numberUtils.formatTime(timeValues[0], "fromseconds");
                    
                    return numberUtils.formatTime(timeValues[1], timeValues[0].toLowerCase(Locale.ROOT));
            }
        }
        
        return null;
    }
    
    // Convenience method to check if any provided String is either null, or empty
    public boolean isNullOrEmpty(String... strings){
        for(String s : strings){
            if(s == null || s.isEmpty())
                return true;
        }
        
        return false;
    }
    
    public String[] getSplit(String text, String split, int length){
        return Arrays.copyOf(text.split(split, length), length);
    }
    
    /*
     * We have this method for backwards-compatability with configurations from before 1.3.2
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isCondensed(){
        Object condensed = this.get("time.condensed", null);
        
        if(condensed == null)
            return false;
        
        if(condensed instanceof String)
            return this.getString("time.condensed", "no").equalsIgnoreCase("yes");
        
        if(condensed instanceof Boolean)
            return this.getBoolean("time.condensed", false);
        
        return true;
    }
}
