package com.serkank.mousemover;

import com.serkank.mousemover.macos.CoreGraphics;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.awt.Robot;
import java.time.Duration;
import java.util.TimerTask;

import static com.serkank.mousemover.macos.CGEventSourceStateID.COMBINED_SESSION_STATE;
import static com.serkank.mousemover.macos.CGEventType.ANY;

@Slf4j
public class MouseMoverTask extends TimerTask {

    private State state = State.UNKNOWN;

    @Override
    public void run() {
        Duration idleTime = getIdleTime();
        State newState = determineState(idleTime);

        if (newState != state) {
            state = newState;
            log.debug("State: {}", state);
            if (state == State.AWAY) {
                log.debug("Activate the mouse wheel to change state!");
                Robot robot = newRobot();
                robot.mouseWheel(-1);
                robot.mouseWheel(1);
            }
        }

    }

    @SneakyThrows
    private static Robot newRobot() {
        return new Robot();
    }

    /**
     * Get Duration that have elapsed since the last input event
     * (mouse or keyboard)
     *
     * @return idle duration
     */
    private static Duration getIdleTimeWin32() {
        User32.LASTINPUTINFO lastInputInfo = new User32.LASTINPUTINFO();
        User32.INSTANCE.GetLastInputInfo(lastInputInfo);
        return Duration.ofMillis(Kernel32.INSTANCE.GetTickCount() - lastInputInfo.dwTime);
    }

    private static Duration getIdleTimeMac() {
        var idleSeconds = CoreGraphics
                .INSTANCE
                .CGEventSourceSecondsSinceLastEventType(COMBINED_SESSION_STATE, ANY);
        return Duration.ofMillis((long) (idleSeconds * 1000));
    }

    private static Duration getIdleTime() {
        var os = System.getProperty("os.name").toLowerCase();
        if (os.contains("mac")) {
            return getIdleTimeMac();
        } else if (os.contains("win")) {
            return getIdleTimeWin32();
        } else {
            throw new UnsupportedOperationException("Unsupported OS: " + os);
        }
    }

    enum State {
        UNKNOWN, ONLINE, IDLE, AWAY
    }

    static State determineState(Duration idleTime) {
        if (idleTime.compareTo(Duration.ofSeconds(30)) < 0) {
            return State.ONLINE;
        }
        if (idleTime.compareTo(Duration.ofMinutes(1)) < 0) {
            return State.IDLE;
        }
        return State.AWAY;
    }

}
