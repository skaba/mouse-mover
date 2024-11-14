package com.serkank.mousemover.macos;

import com.serkank.mousemover.jna.JnaEnum;
import com.serkank.mousemover.jna.ReverseEnumMap;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CGEventSourceStateID implements JnaEnum<CGEventSourceStateID> {

    PRIVATE_STATE(-1),
    COMBINED_SESSION_STATE(0),
    HID_SYSTEM_STATE(1);
    private final int intValue;

    private static ReverseEnumMap<CGEventSourceStateID> MAP = new ReverseEnumMap<>(CGEventSourceStateID.class);

    @Override
    public CGEventSourceStateID getForValue(int i) {
        return MAP.get(i);
    }
}
