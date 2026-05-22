package com.serkank.mousemover.jna.macos;

import com.serkank.mousemover.jna.JnaEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CGEventType implements JnaEnum {
    ANY(~0);

    private int intValue;
}
