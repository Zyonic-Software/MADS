/*
 * 2020. Zyonic Software - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used in compliance with the attached license.
 *
 * Minecraft Application Development System is developed by Tobias Rempe for MineReaperDE
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.mineraper.mads.timers;

import com.zyonicsoftware.mineraper.mads.timingToolBuilder.ClockBuilder;
import com.zyonicsoftware.mineraper.mads.timingToolBuilder.MadsClock;
import org.bukkit.plugin.Plugin;

public class Countdown {

    private final int duration;
    private final ClockBuilder clockBuilder;

    public Countdown(final int duration, final Plugin plugin) {
        this.duration = duration;
        this.clockBuilder = new ClockBuilder(plugin);
    }

    public void startCountdown() {
        final MadsClock madsClock = this.clockBuilder.setType(MadsClock.Type.TIMER).setDelay(0).setPeriod(20).addExecuter(new CountdownExecuter()).build();
    }

}
