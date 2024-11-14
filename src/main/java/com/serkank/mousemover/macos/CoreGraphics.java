package com.serkank.mousemover.macos;

import com.serkank.mousemover.jna.TypeMapper;
import com.sun.jna.Library;
import com.sun.jna.Native;

import java.util.Map;

public interface CoreGraphics extends Library {
    CoreGraphics INSTANCE = createInstance();

    double CGEventSourceSecondsSinceLastEventType(CGEventSourceStateID stateID, CGEventType eventType);

    private static CoreGraphics createInstance() {
        var options = Map.of(Library.OPTION_TYPE_MAPPER, new TypeMapper());
        return Native.load("CoreGraphics", CoreGraphics.class, options);
    }
}
