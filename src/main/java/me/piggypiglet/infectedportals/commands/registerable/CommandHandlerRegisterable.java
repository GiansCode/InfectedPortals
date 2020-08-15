package me.piggypiglet.infectedportals.commands.registerable;

import com.google.inject.Inject;
import me.piggypiglet.infectedportals.bootstrap.framework.Registerable;
import me.piggypiglet.infectedportals.commands.CommandHandler;
import me.piggypiglet.infectedportals.commands.exceptions.NoRegisteredCommandException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class CommandHandlerRegisterable extends Registerable {
    private final JavaPlugin main;
    private final CommandHandler commandHandler;

    @Inject
    public CommandHandlerRegisterable(@NotNull final JavaPlugin main, @NotNull final CommandHandler commandHandler) {
        this.main = main;
        this.commandHandler = commandHandler;
    }

    @Override
    protected void execute() {
        Optional.ofNullable(main.getCommand("ip"))
                .orElseThrow(() -> new NoRegisteredCommandException("The ip command hasn't been registered in the plugin YAML."))
                .setExecutor(commandHandler);
    }
}
