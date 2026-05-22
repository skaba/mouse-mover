package com.serkank.mousemover.jna.macos;

import com.serkank.mousemover.jna.JnaEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CGEventSourceStateID implements JnaEnum {

    PRIVATE_STATE(-1),
    COMBINED_SESSION_STATE(0),
    HID_SYSTEM_STATE(1);
    private final int intValue;
}
