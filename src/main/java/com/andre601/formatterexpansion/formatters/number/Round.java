package com.andre601.formatterexpansion.formatters.number;

import com.andre601.formatterexpansion.FormatterExpansion;
import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.NumberUtils;
import com.andre601.formatterexpansion.utils.StringUtils;

public class Round implements IFormatter{
    @Override
    public String name(){
        return "round";
    }
    
    @Override
    public String parse(String option, String... values){
        if(values.length == 1)
            return NumberUtils.roundNumber(values[0]);
        
        String[] roundingOptions = StringUtils.getSplit(values[0], ":", 2);
        
        int precision = FormatterExpansion.getExpansion().getInt("rounding.precision", 0);
        String rounding = FormatterExpansion.getExpansion().getString("rounding.mode", "half-up");
        
        if(!StringUtils.isNullOrEmpty(roundingOptions[0]))
            precision = StringUtils.parseNumber(roundingOptions[0], precision);
        
        if(!StringUtils.isNullOrEmpty(roundingOptions[1]))
            rounding = roundingOptions[1];
        
        return NumberUtils.roundNumber(StringUtils.merge(1, values), precision, rounding);
    }
}
