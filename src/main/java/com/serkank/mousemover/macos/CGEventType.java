package com.serkank.mousemover.macos;

import com.serkank.mousemover.jna.JnaEnum;
import com.serkank.mousemover.jna.ReverseEnumMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CGEventType implements JnaEnum<CGEventType> {
    ANY(~0);

    private static ReverseEnumMap<CGEventType> MAP = new ReverseEnumMap<>(CGEventType.class);

    private int intValue;

    @Override
    public CGEventType getForValue(int i) {
        return MAP.get(i);
    }
}
