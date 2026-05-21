package com.serkank.mousemover;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.swing.ImageIcon;
import javax.swing.UIManager;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Taskbar;
import java.awt.TrayIcon;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import static com.sun.jna.Platform.isMac;

@Slf4j
public class MouseMover {

    private TimerTask mouseMoverTask;
    private Timer timer;
    private MenuItem enableItem;
    private MenuItem disableItem;

    public static void main(String[] args) {
        new MouseMover().run();
    }

    @SneakyThrows
    public void run() {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        if (!(SystemTray.isSupported() || Taskbar.isTaskbarSupported())) {
            log.error("SystemTray/Taskbar is not supported");
            return;
        }

        PopupMenu menu = new PopupMenu();
        enableItem = new MenuItem("Enable");
        enableItem.addActionListener(e -> schedule());
        menu.add(enableItem);

        disableItem = new MenuItem("Disable");
        disableItem.addActionListener(e -> pause());
        menu.add(disableItem);
        MenuItem closeItem = new MenuItem("Close");
        closeItem.addActionListener(e -> System.exit(0));
        menu.add(closeItem);

        if(isMac() && Taskbar.isTaskbarSupported()) {
            var taskbar = Taskbar.getTaskbar();
            if(taskbar.isSupported(Taskbar.Feature.MENU)) {
                taskbar.setMenu(menu);
                if(taskbar.isSupported(Taskbar.Feature.ICON_IMAGE)) {
                    Image image = createImage("cursor-512.png", "cursor");
                    taskbar.setIconImage(image);
                }
            } else {
                setupTray(menu);
            }
        } else {
            setupTray(menu);
        }

        schedule();
    }

    @SneakyThrows
    private void setupTray(PopupMenu menu) {
        SystemTray tray = SystemTray.getSystemTray();
        Image image = createImage("cursor-512.png", "cursor");

        TrayIcon icon = new TrayIcon(image, "Mouse Mover", menu);
        icon.setImageAutoSize(true);

        tray.add(icon);
    }

    public void pause() {
        if (timer != null) {
            timer.cancel();
        }
        mouseMoverTask = null;
        timer = null;
        enableItem.setEnabled(true);
        disableItem.setEnabled(false);
    }

    public void schedule() {
        pause();
        timer = new Timer();
        mouseMoverTask = new MouseMoverTask();
        timer.schedule(mouseMoverTask, 0, 1000 * 10);
        enableItem.setEnabled(false);
        disableItem.setEnabled(true);
    }

    // Obtain the image URL
    protected static Image createImage(String path, String description) {
        return Optional
                .ofNullable(MouseMover.class.getClassLoader().getResource(path))
                .map(imageURL -> new ImageIcon(imageURL, description))
                .map(ImageIcon::getImage)
                .orElseThrow(RuntimeException::new);
    }
}
