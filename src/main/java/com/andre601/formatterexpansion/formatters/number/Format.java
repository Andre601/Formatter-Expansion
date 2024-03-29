package com.andre601.formatterexpansion.formatters.number;

import com.andre601.formatterexpansion.FormatterExpansion;
import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.NumberUtils;
import com.andre601.formatterexpansion.utils.StringUtils;
import com.andre601.formatterexpansion.utils.logging.CachedWarnHelper;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Format implements IFormatter{
    
    private final FormatterExpansion expansion;
    
    public Format(FormatterExpansion expansion){
        this.expansion = expansion;
    }
    
    @Override
    public String name(){
        return "format";
    }
    
    @Override
    public String parse(String raw, String option, String... values){
        String locale = expansion.getString("formatting.locale", "en-US");
        String pattern = expansion.getString("formatting.pattern", "#,###,###.##");
        
        // %formatter_number_format_<number>%
        if(values.length == 1 || !values[0].contains(":"))
            return formatNumber(raw, String.join("", values), locale, pattern);
        
        String[] options = StringUtils.getSplit(values[0], ":", 2);
        
        if(!StringUtils.isNullOrEmpty(options[0]))
            locale = options[0];
        
        if(!StringUtils.isNullOrEmpty(options[1]))
            pattern = options[1];
        
        return formatNumber(raw, StringUtils.merge(1, "", values), locale, pattern);
    }
    
    private String formatNumber(String raw, String number, String locale, String format){
        // Allow arbitrary numbers
        BigDecimal decimal = NumberUtils.getBigDecimal(number);
        if(decimal == null){
            CachedWarnHelper.warn(expansion, raw, "Cannot convert " + number + " into a BigDecimal.");
            return null;
        }
        
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
