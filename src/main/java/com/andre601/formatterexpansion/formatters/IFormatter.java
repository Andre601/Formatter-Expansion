package com.andre601.formatterexpansion.formatters;

public interface IFormatter{
    String name();
    
    String parse(String option, String... values);
}
