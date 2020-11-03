/*
 * 2020. Zyonic Software - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used in compliance with the attached license.
 *
 * Minecraft Application Development System is developed by Tobias Rempe for MineReaperDE
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.mineraper.mads.serverTools;

import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Objective;

public class ScoreboardBuilder {

    private final Objective objective;

    public ScoreboardBuilder(final Plugin plugin, final String name, final String title, final String criteria) {
        this.objective = plugin.getServer().getScoreboardManager().getNewScoreboard().registerNewObjective(name, criteria, title);
    }

    public ScoreboardBuilder(final Plugin plugin, final String title) {
        this.objective = plugin.getServer().getScoreboardManager().getNewScoreboard().registerNewObjective(plugin.getName() + "-MADS-Scoreboard", "criteria", title);
    }

}
