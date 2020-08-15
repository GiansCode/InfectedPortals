package me.piggypiglet.infectedportals.file.objects.deserialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import org.jetbrains.annotations.NotNull;
import sh.okx.timeapi.TimeAPI;

import java.lang.reflect.Type;
import java.util.regex.Pattern;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class TimeDeserializer implements JsonDeserializer<Long[]> {
    private static final Pattern TIME_RANGE_PATTERN = Pattern.compile("-");

    @NotNull
    @Override
    public Long[] deserialize(final JsonElement json, final Type typeOfT,
                              final JsonDeserializationContext context) {
        final String[] split = TIME_RANGE_PATTERN.split(json.getAsString());

        if (split.length != 2) {
            System.out.println(json.getAsString() + " is not a valid time range.");
            return new Long[]{20L, 60L};
        }

        return new Long[]{
                new TimeAPI(split[0]).getTicks(),
                new TimeAPI(split[1]).getTicks()
        };
    }
}
