package com.andre601.formatterexpansion.formatters.number;

import com.andre601.formatterexpansion.FormatterExpansion;
import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.NumberUtils;
import com.andre601.formatterexpansion.utils.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Format implements IFormatter{
    
    private final FormatterExpansion expansion;
    private final NavigableMap<Long, String> suffixes = new TreeMap<>();
    
    public Format(FormatterExpansion expansion){
        this.expansion = expansion;
        
        suffixes.put(1_000L, expansion.getString("formatting.thousands", "K"));
        suffixes.put(1_000_000L, expansion.getString("formatting.millions", "M"));
        suffixes.put(1_000_000_000L, expansion.getString("formatting.billions", "B"));
        suffixes.put(1_000_000_000_000L, expansion.getString("formatting.trillions", "T"));
        suffixes.put(1_000_000_000_000_000L, expansion.getString("formatting.quadrillions", "Q"));
    }
    
    @Override
    public String name(){
        return "format";
    }
    
    @Override
    public String parse(String option, String... values){
        String locale = expansion.getString("formatting.locale", "en-US");
        String pattern = expansion.getString("formatting.pattern", "#,###,###.##");
        
        // %formatter_number_format_<number>%
        if(values.length == 1 || !values[0].contains(":"))
            return formatNumber(String.join("", values), locale, pattern);
        
        // %formatter_number_format_truncate_<number>%
        if(values[0].equalsIgnoreCase("truncate"))
            return truncateNumber(NumberUtils.optLong(StringUtils.merge(1, "", values)));
        
        String[] options = StringUtils.getSplit(values[0], ":", 2);
        
        if(!StringUtils.isNullOrEmpty(options[0]))
            locale = options[0];
        
        if(!StringUtils.isNullOrEmpty(options[1]))
            pattern = options[1];
        
        return formatNumber(StringUtils.merge(1, "", values), locale, pattern);
    }
    
    /*
     * Source from StackOverflow: https://stackoverflow.com/a/30661479/11496439
     */
    private String truncateNumber(Long value){
        if(value == null)
            return null;
        
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
    
    private String formatNumber(String number, String locale, String format){
        // Allow arbitrary numbers
        BigDecimal decimal = NumberUtils.getBigDecimal(number);
        if(decimal == null)
            return null;
        
        Locale loc = getLocale(locale);
        NumberFormat numberFormat = NumberFormat.getNumberInstance(loc);
        DecimalFormat decimalFormat = (DecimalFormat)numberFormat;
        
        decimalFormat.applyPattern(format);
        
        return decimalFormat.format(decimal);
    }
    
    private Locale getLocale(String input){
        if(input.contains("-")){
            String[] args = StringUtils.getSplit(input, "-", 2);
            if(!StringUtils.isNullOrEmpty(args[0], args[1])){
                return new Locale(args[0], args[1]);
            }else
            if(!StringUtils.isNullOrEmpty(args[0])){
                return new Locale(args[0]);
            }else{
                return Locale.US;
            }
        }else{
            return new Locale(input);
        }
    }
}
