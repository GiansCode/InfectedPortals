package me.piggypiglet.infectedportals.commands.registerable;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.piggypiglet.infectedportals.bootstrap.framework.Registerable;
import me.piggypiglet.infectedportals.commands.CommandHandler;
import me.piggypiglet.infectedportals.commands.framework.Command;
import me.piggypiglet.infectedportals.scanning.framework.Scanner;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class CommandsRegisterable extends Registerable {
    private final Scanner scanner;
    private final CommandHandler commandHandler;

    @Inject
    public CommandsRegisterable(@NotNull final Scanner scanner, @NotNull final CommandHandler commandHandler) {
        this.scanner = scanner;
        this.commandHandler = commandHandler;
    }

    @Override
    protected void execute(@NotNull final Injector injector) {
        commandHandler.populate(scanner.getSubTypesOf(Command.class).stream()
                .map(injector::getInstance)
                .collect(Collectors.toSet()));
    }
}
