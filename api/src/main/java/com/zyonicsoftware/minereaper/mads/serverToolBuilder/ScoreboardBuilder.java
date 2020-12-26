/*
 * 2020. Zyonic Software - Tobias Rempe
 * This File, its contents and by extention the corresponding project may be used in compliance with the attached license.
 *
 * Minecraft Application Development System is developed by Tobias Rempe for MineReaperDE
 *
 * tobiasrempe@zyonicsoftware.com
 */

package com.zyonicsoftware.minereaper.mads.serverToolBuilder;

import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardBuilder {

    private final Scoreboard scoreboard;
    private final Objective objective;

    private int numberOfVariables = 0;

    public ScoreboardBuilder(final Plugin plugin, final String name, final String title, final String criteria) {
        this.scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective(name, criteria, title);
    }

    public ScoreboardBuilder(final Plugin plugin, final String title) {
        this.scoreboard = plugin.getServer().getScoreboardManager().getNewScoreboard();
        this.objective = this.scoreboard.registerNewObjective(plugin.getName() + "-MADS", "criteria", title);
    }

    public ScoreboardBuilder(final Scoreboard scoreboard, final Objective objective) {
        this.scoreboard = scoreboard;
        this.objective = objective;
    }

    public ScoreboardBuilder setDisplaySlot(final DisplaySlot displaySlot) {
        this.objective.setDisplaySlot(displaySlot);
        return this;
    }

    /**
     * Creates a new "line" in the Scoreboard
     *
     * @param content  The content that will be displayed
     * @param position Where the specefied will be displayed. The line with the highest number will be displayed on top
     * @return Returns the edited version of the ScoreboardBuilder so you can stack methods
     */
    public ScoreboardBuilder registerNewLine(final String content, final int position) {
        this.objective.getScore(content).setScore(position);
        return this;
    }

    public ScoreboardBuilder registerNewVariableLine(final String variableName, final String variableHeader, final String content, final int position) {
        final Team team = this.scoreboard.registerNewTeam(variableName);
        this.objective.getScore("§" + this.numberOfVariables + variableHeader + " §7").setScore(position);
        team.addEntry("§" + this.numberOfVariables + variableHeader + " §7");
        team.setSuffix(content);
        this.numberOfVariables++;
        return this;
    }

    public ScoreboardBuilder updateVariableLine(final String variableName, final String updatedContent) {
        this.scoreboard.getTeam(variableName).setSuffix(updatedContent);
        return this;
    }

    public Scoreboard build() {
        return this.objective.getScoreboard();
    }

    public Scoreboard getRawScoreboard() {
        return this.scoreboard;
    }

    public Objective getObjective() {
        return this.objective;
    }
}
