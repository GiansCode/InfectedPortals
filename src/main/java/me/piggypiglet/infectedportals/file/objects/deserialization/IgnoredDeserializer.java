package me.piggypiglet.infectedportals.file.objects.deserialization;

import com.google.common.reflect.TypeToken;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Set;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class IgnoredDeserializer implements JsonDeserializer<Set<Material>> {
    private static final Type DESERIALIZED_RAW_SET = new TypeToken<Set<String>>() {}.getType();

    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    public Set<Material> deserialize(@NotNull final JsonElement json, @NotNull final Type typeOfT,
                                     @NotNull final JsonDeserializationContext context) {
        return ((Set<String>) context.deserialize(json, DESERIALIZED_RAW_SET)).stream()
                .map(String::toUpperCase)
                .map(Material::valueOf)
                .collect(Collectors.toSet());
    }
}
