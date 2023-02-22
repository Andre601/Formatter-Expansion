package com.andre601.formatterexpansion.utils.logging;

import me.clip.placeholderapi.PlaceholderAPIPlugin;

import java.util.logging.Logger;

public class LegacyLogger implements LoggerUtil{
    
    private final Logger logger;
    
    public LegacyLogger(){
        logger = PlaceholderAPIPlugin.getInstance().getLogger();
    }
    
    @Override
    public void info(String msg){
        logger.info("[Formatter] " + msg);
    }
    
    @Override
    public void warn(String msg){
        logger.warning("[Formatter] " + msg);
    }
}
