package me.piggypiglet.infectedportals.commands.exceptions;

import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class NoDefaultCommandException extends RuntimeException {
    public NoDefaultCommandException(@NotNull final String message) {
        super(message);
    }
}
