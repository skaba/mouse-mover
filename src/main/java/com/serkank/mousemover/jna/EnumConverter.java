package com.serkank.mousemover.jna;

import com.sun.jna.FromNativeContext;
import com.sun.jna.FromNativeConverter;
import com.sun.jna.ToNativeContext;
import com.sun.jna.TypeConverter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;

@Slf4j
class EnumConverter implements TypeConverter {

    private final ConcurrentHashMap<Class, ReverseEnumMap<?>> lookup = new ConcurrentHashMap<>();

    public Object fromNative(Object input, FromNativeContext context) {
        Integer i = (Integer) input;
        Class targetClass = context.getTargetType();
        if (!JnaEnum.class.isAssignableFrom(targetClass)) {
            return null;
        }

        var map = lookup.computeIfAbsent(targetClass, ReverseEnumMap::of);
        return map.get(i);
    }

    public Object toNative(Object input, ToNativeContext context) {
        JnaEnum j = (JnaEnum) input;
        return j.getIntValue();
    }

    public Class nativeType() {
        return Integer.class;
    }
}