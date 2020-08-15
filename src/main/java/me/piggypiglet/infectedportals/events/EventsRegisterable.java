package me.piggypiglet.infectedportals.events;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.piggypiglet.infectedportals.bootstrap.framework.Registerable;
import me.piggypiglet.infectedportals.scanning.framework.Scanner;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class EventsRegisterable extends Registerable {
    private final Scanner scanner;
    private final JavaPlugin main;

    @Inject
    public EventsRegisterable(@NotNull final Scanner scanner, @NotNull final JavaPlugin main) {
        this.scanner = scanner;
        this.main = main;
    }

    @Override
    protected void execute(final @NotNull Injector injector) {
        scanner.getSubTypesOf(Listener.class).stream()
                .map(injector::getInstance)
                .forEach(listener -> Bukkit.getServer().getPluginManager().registerEvents(listener, main));
    }
}
