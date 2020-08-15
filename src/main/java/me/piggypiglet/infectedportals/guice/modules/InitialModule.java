package me.piggypiglet.infectedportals.guice.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import me.piggypiglet.infectedportals.bootstrap.InfectedPortalsBootstrap;
import me.piggypiglet.infectedportals.scanning.framework.Scanner;
import me.piggypiglet.infectedportals.scanning.implementations.ZISScanner;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class InitialModule extends AbstractModule {
    private static final Class<InfectedPortalsBootstrap> MAIN_CLASS = InfectedPortalsBootstrap.class;
    private static final String PACKAGE = "me/piggypiglet/infectedportals";

    private final JavaPlugin main;

    public InitialModule(@NotNull final JavaPlugin main) {
        this.main = main;
    }

    @Provides
    @Singleton
    public JavaPlugin providesMain() {
        return main;
    }

    @Provides
    @Singleton
    public Scanner providesScanner() {
        return ZISScanner.create(MAIN_CLASS, PACKAGE);
    }
}
