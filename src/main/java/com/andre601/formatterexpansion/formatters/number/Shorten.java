package com.andre601.formatterexpansion.formatters.number;

import com.andre601.formatterexpansion.FormatterExpansion;
import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.NumberUtils;
import com.andre601.formatterexpansion.utils.logging.CachedWarnHelper;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Shorten implements IFormatter{
    
    private final FormatterExpansion expansion;
    private final NavigableMap<Long, String> suffixes = new TreeMap<>();
    
    public Shorten(FormatterExpansion expansion){
        this.expansion = expansion;
        suffixes.put(1_000L, expansion.getString("shorten.thousands", "K"));
        suffixes.put(1_000_000L, expansion.getString("shorten.millions", "M"));
        suffixes.put(1_000_000_000L, expansion.getString("shorten.billions", "B"));
        suffixes.put(1_000_000_000_000L, expansion.getString("shorten.trillions", "T"));
        suffixes.put(1_000_000_000_000_000L, expansion.getString("shorten.quadrillions", "Q"));
    }
    
    @Override
    public String name(){
        return "shorten";
    }
    
    @Override
    public String parse(String raw, String option, String... values){
        Long value = NumberUtils.optLong(String.join("", values));
        if(value == null){
            CachedWarnHelper.warn(expansion, raw, "Cannot convert " + String.join("", values) + " to a long.");
            return null;
        }
        
        return truncateNumber(value);
    }
    
    /*
     * Source from StackOverflow: https://stackoverflow.com/a/30661479/11496439
     */
    private String truncateNumber(long value){
        if(value == Long.MIN_VALUE)
            return truncateNumber(Long.MIN_VALUE + 1);
        
        if(value < 0)
            return "-" + truncateNumber(-value);
        
        if(value < 1000)
            return Long.toString(value);
        
        Map.Entry<Long, String> entry = suffixes.floorEntry(value);
        long divideBy = entry.getKey();
        String suffix = entry.getValue();
        
        long truncated = value / (divideBy / 10);
        boolean hasDecimal = truncated < 100 && (truncated / 10d) != (truncated / 10);
        
        return hasDecimal ? (truncated / 10d) + suffix : (truncated / 10) + suffix;
    }
}
