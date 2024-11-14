package com.serkank.mousemover.jna;

public interface JnaEnum<E extends Enum<E>> {
    int getIntValue();

    E getForValue(int i);
}
