package com.serkank.mousemover.jna.macos;

import com.serkank.mousemover.jna.JnaEnum;
import com.serkank.mousemover.jna.ReverseEnumMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CGEventType implements JnaEnum<CGEventType> {
    ANY(~0);

    private int intValue;
}
