package me.piggypiglet.infectedportals.file.deserialization;

import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class YamlFileDeserializer implements FileDeserializer {
    private static final Yaml YAML = new Yaml();

    @NotNull
    @Override
    public Map<String, Object> deserialize(final @NotNull String content) {
        return YAML.load(content);
    }
}
