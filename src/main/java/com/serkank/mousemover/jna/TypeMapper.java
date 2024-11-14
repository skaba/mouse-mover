package com.serkank.mousemover.jna;

import com.sun.jna.DefaultTypeMapper;

public class TypeMapper extends DefaultTypeMapper {

    public TypeMapper() {

        // The EnumConverter is set to fire when instances of 
        // our interface, JnaEnum, are seen.
        addTypeConverter(JnaEnum.class, new EnumConverter());
    }
}