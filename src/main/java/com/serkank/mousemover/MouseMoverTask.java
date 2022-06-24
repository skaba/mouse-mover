package com.serkank.mousemover;

import java.awt.Robot;
import java.time.Duration;
import java.util.TimerTask;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.User32;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MouseMoverTask extends TimerTask {

    private State state = State.UNKNOWN;

    @Override
    public void run() {
        Duration idleTime = getIdleTimeWin32();
        State newState = idleTime.compareTo(Duration.ofSeconds(30)) < 0 ? State.ONLINE
                : idleTime.compareTo(Duration.ofMinutes(1)) > 0 ? State.AWAY : State.IDLE;

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

    private static enum State {
        UNKNOWN, ONLINE, IDLE, AWAY
    }

}
