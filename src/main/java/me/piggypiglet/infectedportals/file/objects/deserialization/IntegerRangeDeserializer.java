package me.piggypiglet.infectedportals.file.objects.deserialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.regex.Pattern;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class IntegerRangeDeserializer implements JsonDeserializer<Integer[]> {
    private static final Pattern RANGE_DELIMITER = Pattern.compile("-");

    @NotNull
    @Override
    public Integer[] deserialize(@NotNull final JsonElement json, @NotNull final Type typeOfT,
                                 @NotNull final JsonDeserializationContext context) {
        final String[] parts = RANGE_DELIMITER.split(json.getAsString());
        return new Integer[]{Integer.parseInt(parts[0].trim()), Integer.parseInt(parts[1].trim())};
    }
}
