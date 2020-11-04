/*
 * 2020. Zyonic Software - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used in compliance with the attached license.
 *
 * Minecraft Application Development System is developed by Tobias Rempe for MineReaperDE
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.mineraper.mads.timingToolBuilder;

import org.bukkit.plugin.Plugin;

import java.util.Collection;

public class ClockBuilder {

    private final Plugin plugin;
    private int delay = 20;
    private int period = 20;
    private MadsClock.Type type;
    private Collection<ClockExecuter> executers;

    public ClockBuilder(final Plugin plugin) {
        this.plugin = plugin;
    }

    public ClockBuilder setDelay(final int delay) {
        this.delay = delay;
        return this;
    }

    public ClockBuilder setPeriod(final int period) {
        this.period = period;
        return this;
    }

    public ClockBuilder setType(final MadsClock.Type type) {
        this.type = type;
        return this;
    }

    public ClockBuilder addExecuter(final ClockExecuter executer) {
        this.executers.add(executer);
        return this;
    }

    public ClockBuilder addExecuters(final Collection<ClockExecuter> executers) {
        this.executers.addAll(executers);
        return this;
    }

    public MadsClock build() {
        return new MadsClock(this.type, this.plugin, this.delay, this.period, this.executers);
    }
}
