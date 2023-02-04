package com.andre601.formatterexpansion.utils.logging;

import com.andre601.formatterexpansion.FormatterExpansion;

public class NativeLogger implements LoggerUtil{
    
    private final FormatterExpansion expansion;
    
    public NativeLogger(FormatterExpansion expansion){
        this.expansion = expansion;
    }
    
    @Override
    public void info(String msg){
        expansion.info(msg);
    }
}
