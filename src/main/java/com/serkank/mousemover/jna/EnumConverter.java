package com.serkank.mousemover.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.ToNativeContext;
import com.sun.jna.TypeConverter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class EnumConverter implements TypeConverter {

    public Object fromNative(Object input, FromNativeContext context) {
        Integer i = (Integer) input;
        Class targetClass = context.getTargetType();
        if (!JnaEnum.class.isAssignableFrom(targetClass)) {
            return null;
        }
        Object[] enums = targetClass.getEnumConstants();
        if (enums.length == 0) {
            log.error("Could not convert desired enum type (), no valid values are defined.", targetClass.getName());
            return null;
        }
        // In order to avoid nasty reflective junk and to avoid needing
        // to know about every subclass of JnaEnum, we retrieve the first
        // element of the enum and make IT do the conversion for us.

        JnaEnum instance = (JnaEnum) enums[0];
        return instance.getForValue(i);

    }

    public Object toNative(Object input, ToNativeContext context) {
        JnaEnum j = (JnaEnum) input;
        return Integer.valueOf(j.getIntValue());
    }

    public Class nativeType() {
        return Integer.class;
    }
}