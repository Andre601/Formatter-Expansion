package com.andre601.formatterexpansion;

import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.formatters.number.NumberFormatter;
import com.andre601.formatterexpansion.formatters.text.TextFormatter;
import com.andre601.formatterexpansion.utils.StringUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nonnull;
import java.util.*;

public class FormatterExpansion extends PlaceholderExpansion implements Configurable{
    
    private final Map<String, Object> defaults = new HashMap<>();
    private final List<IFormatter> formatters;
    
    public FormatterExpansion(){
        loadDefaults();
        this.formatters = Arrays.asList(
            new NumberFormatter(this),
            new TextFormatter()
        );
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
    public String onRequest(OfflinePlayer player, @Nonnull String identifier){
        identifier = PlaceholderAPI.setBracketPlaceholders(player, identifier);
        String[] temp = StringUtils.getSplit(identifier, "_", 3);
        
        if(StringUtils.isNullOrEmpty(temp[0], temp[1], temp[2]))
            return null;
        
        for(IFormatter formatter : formatters){
            if(formatter.name().equalsIgnoreCase(temp[0]))
                return formatter.parse(temp[1], temp[2].split("_"));
        }
        
        return null;
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
    
    private void loadDefaults(){
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
}
