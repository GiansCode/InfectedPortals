package me.piggypiglet.infectedportals.task;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class Task {
    private static final BukkitScheduler SCHEDULER = Bukkit.getScheduler();

    private final JavaPlugin main;

    @Inject
    public Task(@NotNull final JavaPlugin main) {
        this.main = main;
    }

    public void syncLater(@NotNull final Runnable task, final long ticks) {
        SCHEDULER.runTaskLater(main, task, ticks);
    }
}
