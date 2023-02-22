package com.andre601.formatterexpansion.formatters.text;

import com.andre601.formatterexpansion.FormatterExpansion;
import com.andre601.formatterexpansion.formatters.IFormatter;
import com.andre601.formatterexpansion.utils.StringUtils;
import com.andre601.formatterexpansion.utils.logging.CachedWarnHelper;

public class Replace implements IFormatter{
    
    private final FormatterExpansion expansion;
    
    public Replace(FormatterExpansion expansion){
        this.expansion = expansion;
    }
    
    @Override
    public String name(){
        return "replace";
    }
    
    @Override
    public String parse(String raw, String option, String... values){
        if(values.length < 3){
            CachedWarnHelper.warn(expansion, raw, "Placeholder does not have a [target], [replacement] and text value.");
            return null;
        }
        
        String target = values[0].replace("{{u}}", "_");
        String replacement = values[1].replace("{{u}}", "_");
        
        return StringUtils.merge(2, "_", values).replace(target, replacement);
    }
}
