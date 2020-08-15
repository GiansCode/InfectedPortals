package me.piggypiglet.infectedportals.commands.implementations;

import com.google.inject.Inject;
import me.piggypiglet.infectedportals.commands.framework.Command;
import me.piggypiglet.infectedportals.file.FileManager;
import me.piggypiglet.infectedportals.file.annotations.File;
import me.piggypiglet.infectedportals.file.objects.Config;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class ReloadCommand extends Command {
    private final FileManager fileManager;

    @Inject
    public ReloadCommand(@NotNull final FileManager fileManager) {
        super("reload");
        this.fileManager = fileManager;
        options
                .permissions("infectedportals.admin", "infectedportals.reload")
                .description("Reload the plugin's config.")
                .usage("");
    }

    @Override
    public boolean execute(final @NotNull CommandSender sender, final @NotNull String[] args) {
        final File data = Config.class.getAnnotation(File.class);
        fileManager.loadFile(Config.class, data.internalPath(), data.externalPath());
        sender.sendMessage("&7Successfully loaded latest config values into memory.");
        return true;
    }
}
