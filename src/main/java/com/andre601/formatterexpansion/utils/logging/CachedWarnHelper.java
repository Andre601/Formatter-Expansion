package com.andre601.formatterexpansion.utils.logging;

import com.andre601.formatterexpansion.FormatterExpansion;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class CachedWarnHelper{
    
    private static final Cache<String, String> cachedWarns = CacheBuilder.newBuilder()
        .expireAfterWrite(10, TimeUnit.SECONDS)
        .build();
    
    public static void warn(FormatterExpansion expansion, String placeholder, String msg){
        warn(expansion, "", placeholder, msg);
    }
    
    public static void warn(FormatterExpansion expansion, String suffix, String placeholder, String msg){
        String identifier = placeholder + (suffix.isEmpty() ? "" : "." + suffix);
        if(cachedWarns.getIfPresent(identifier) != null)
            return;
        
        cachedWarns.put(identifier, placeholder);
        
        expansion.getLogger().warn("Invalid placeholder " + placeholder);
        expansion.getLogger().warn(msg);
    }
}
