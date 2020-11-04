/*
 * 2020. Zyonic Software - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used in compliance with the attached license.
 *
 * Minecraft Application Development System is developed by Tobias Rempe for MineReaperDE
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.mineraper.mads.timingToolBuilder;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;

public class MadsClock {

    private final Type clocktype;
    private final Plugin plugin;
    private final int delay;
    private final int period;
    private int processID;
    private BukkitTask task;
    private final Collection<ClockExecuter> executers;

    public enum Type {
        SYNCREPEATING,
        ASYNCTIMER,
        TIMER
    }
    
    public MadsClock(final Type type, final Plugin plugin, final int delay, final int period, final Collection<ClockExecuter> executers) {
        this.clocktype = type;
        this.plugin = plugin;
        this.delay = delay;
        this.period = period;
        this.executers = executers;
        this.initClock();
    }

    private void initClock() {
        switch (this.clocktype) {
            case TIMER:
                this.startTimerClock();
                break;
            case SYNCREPEATING:
                this.startSyncRepeatingClock();
                break;
            case ASYNCTIMER:
                this.startAsyncTimerClock();
                break;
        }
    }

    private void startSyncRepeatingClock() {
        this.processID = Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {
            this.executers.forEach(ClockExecuter::execute);
        }, this.delay, this.period);
    }

    private void startTimerClock() {
        this.task = Bukkit.getScheduler().runTaskTimer(this.plugin, () -> {
            this.executers.forEach(ClockExecuter::execute);
        }, this.delay, this.period);
    }

    private void startAsyncTimerClock() {
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, () -> {
            this.executers.forEach(ClockExecuter::execute);
        }, this.delay, this.period);
    }

    public void stopClock() {
        if (this.processID != 0) {
            Bukkit.getScheduler().cancelTask(this.processID);
        } else if (this.task != null) {
            this.task.cancel();
        }
    }

}
