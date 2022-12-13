package com.andre601.formatterexpansion.formatters.number;

import com.andre601.formatterexpansion.FormatterExpansion;
import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.NumberUtils;
import com.andre601.formatterexpansion.utils.StringUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Locale;

public class Round implements IFormatter{
    
    private final FormatterExpansion expansion;
    
    public Round(FormatterExpansion expansion){
        this.expansion = expansion;
    }
    
    @Override
    public String name(){
        return "round";
    }
    
    @Override
    public String parse(String option, String... values){
        int precision = expansion.getInt("rounding.precision", 0);
        String rounding = expansion.getString("rounding.mode", "half-up");
        
        if(values.length == 1)
            return roundNumber(values[0], precision, rounding);
        
        String[] roundingOptions = StringUtils.getSplit(values[0], ":", 2);
        
        if(!StringUtils.isNullOrEmpty(roundingOptions[0]))
            precision = NumberUtils.parseNumber(roundingOptions[0]);
        
        if(!StringUtils.isNullOrEmpty(roundingOptions[1]))
            rounding = roundingOptions[1];
        
        return roundNumber(StringUtils.merge(1, "", values), precision, rounding);
    }
    
    private String roundNumber(String number, int precision, String roundingMode){
        // Allow arbitrary numbers
        BigDecimal decimal = NumberUtils.getBigDecimal(number);
        if(decimal == null)
            return null;
        
        if(precision < 0)
            precision = 0; // Making sure precision isn't negative
        
        RoundingMode mode = getRoundingMode(roundingMode);
        MathContext context = new MathContext(precision, mode);
        
        return decimal.round(context).toPlainString();
    }
    
    private RoundingMode getRoundingMode(String roundingMode){
        return switch(roundingMode.toLowerCase(Locale.ROOT)){
            case "up" -> RoundingMode.UP;
            case "down" -> RoundingMode.DOWN;
            case "ceiling" -> RoundingMode.CEILING;
            case "floor" -> RoundingMode.FLOOR;
            case "half-down" -> RoundingMode.HALF_DOWN;
            case "half-even" -> RoundingMode.HALF_EVEN;
            default -> RoundingMode.HALF_UP;
        };
    }
}
