package me.piggypiglet.infectedportals.utils.material;

import me.piggypiglet.infectedportals.bootstrap.InfectedPortalsBootstrap;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class MaterialUtils {
    private static final Logger LOGGER = JavaPlugin.getProvidingPlugin(InfectedPortalsBootstrap.class).getLogger();

    private MaterialUtils() {
        throw new AssertionError("This class cannot be instantiated.");
    }

    @Nullable
    public static Material parseMaterial(@NotNull final String material) {
        try {
            return Material.valueOf(material.toUpperCase());
        } catch (final Exception exception) {
            LOGGER.warning(material + " is not a valid material.");
        }

        return null;
    }
}
