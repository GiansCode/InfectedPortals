package me.piggypiglet.infectedportals.file.deserialization;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public interface FileDeserializer {
    @NotNull
    Map<String, Object> deserialize(@NotNull final String content);
}
