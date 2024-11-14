package com.serkank.mousemover.jna;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReverseEnumMap<E extends Enum<E> & JnaEnum<E>> {
    private Map<Integer, E> map;

    public ReverseEnumMap(Class<E> valueType) {
        map = Arrays.stream(valueType.getEnumConstants()).collect(Collectors.toMap(JnaEnum::getIntValue, Function.identity()));
    }

    public E get(int num) {
        return map.get(num);
    }
}