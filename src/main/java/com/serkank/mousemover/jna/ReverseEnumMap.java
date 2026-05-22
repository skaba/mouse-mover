package com.serkank.mousemover.jna;

import java.util.Arrays;
import java.util.Map;
import static java.util.function.Function.identity;
import java.util.stream.Collectors;

public class ReverseEnumMap<E extends Enum<E> & JnaEnum> {
    private Map<Integer, E> map;

    private ReverseEnumMap(Class<E> valueType) {
        map = Arrays.stream(valueType.getEnumConstants()).collect(Collectors.toMap(JnaEnum::getIntValue, identity()));
    }

    public E get(int num) {
        return map.get(num);
    }

    public static <E extends Enum<E> & JnaEnum> ReverseEnumMap<E> of(Class<E> clazz) {
        return new ReverseEnumMap<>(clazz);
    }
}