package com.andre601.formatterexpansion;

import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.formatters.number.NumberFormatter;
import com.andre601.formatterexpansion.formatters.text.TextFormatter;
import com.andre601.formatterexpansion.utils.StringUtils;
import com.andre601.formatterexpansion.utils.logging.CachedWarnHelper;
import com.andre601.formatterexpansion.utils.logging.LegacyLogger;
import com.andre601.formatterexpansion.utils.logging.LoggerUtil;
import com.andre601.formatterexpansion.utils.logging.NativeLogger;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.Configurable;
import me.clip.placeholderapi.expansion.NMSVersion;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FormatterExpansion extends PlaceholderExpansion implements Configurable{
    
    private final Map<String, Object> defaults = new HashMap<>();
    private final List<IFormatter> formatters;
    private final LoggerUtil logger;
    
    public FormatterExpansion(){
        this.logger = loadLogger();
        loadDefaults();
        this.formatters = Arrays.asList(
            new NumberFormatter(this),
            new TextFormatter(this)
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
        migrate();
        
        return defaults;
    }
    
    @Override
    public String onRequest(OfflinePlayer player, @Nonnull String identifier){
        String raw = "%formatter_" + identifier + "%";
        identifier = PlaceholderAPI.setBracketPlaceholders(player, identifier);
        String[] temp = StringUtils.getSplit(identifier, "_", 3);
        
        if(StringUtils.isNullOrEmpty(temp[0], temp[1], temp[2])){
            CachedWarnHelper.warn(this, raw, "Placeholder needs to be of format '%formatter_<type>_<option>_<values>%'");
            return null;
        }
        
        for(IFormatter formatter : formatters){
            if(formatter.name().equalsIgnoreCase(temp[0]))
                return formatter.parse(raw, temp[1], temp[2].split("_"));
        }
        
        CachedWarnHelper.warn(this, raw, "Unknown placeholder type '" + temp[0] + "'.");
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
        
        if(condensed instanceof String condensedString)
            return condensedString.equalsIgnoreCase("yes");
        
        if(condensed instanceof Boolean condensedBool)
            return condensedBool;
        
        return false;
    }
    
    public LoggerUtil loadLogger(){
        if(NMSVersion.getVersion("v1_18_R1") != NMSVersion.UNKNOWN)
            return new NativeLogger(this);
        
        return new LegacyLogger();
    }
    
    public LoggerUtil getLogger(){
        return logger;
    }
    
    private void loadDefaults(){
        defaults.put("formatting.pattern", "#,###,###.##");
        defaults.put("formatting.locale", "en-US");
        
        defaults.put("shorten.thousands", "K");
        defaults.put("shorten.millions", "M");
        defaults.put("shorten.billions", "B");
        defaults.put("shorten.trillions", "T");
        defaults.put("shorten.quadrillions", "Q");
    
        defaults.put("time.milliseconds", "ms");
        defaults.put("time.seconds", "s");
        defaults.put("time.minutes", "m");
        defaults.put("time.hours", "h");
        defaults.put("time.days", "d");
        defaults.put("time.condensed", false);
    
        defaults.put("rounding.precision", 0);
        defaults.put("rounding.mode", "half-up");
    }
    
    private void migrate(){
        if(this.getString("format", null) == null)
            return;
        
        logger.info("Found old 'format' option. Try to migrate...");
        
        defaults.put("format", null);
        defaults.put("locale", null);
        defaults.put("formatting.pattern", this.getString("format", "#,###,###.##"));
        defaults.put("formatting.locale", this.getString("locale", "en-US"));
        
        logger.info("Migration completed! Please check the config.yml for any issues.");
    }
}
