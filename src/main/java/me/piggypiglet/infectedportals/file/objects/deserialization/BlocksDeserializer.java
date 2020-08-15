package me.piggypiglet.infectedportals.file.objects.deserialization;

import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import me.piggypiglet.infectedportals.bootstrap.InfectedPortalsBootstrap;
import me.piggypiglet.infectedportals.utils.collection.ProbabilityCollection;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class BlocksDeserializer implements JsonDeserializer<Map<Material, ProbabilityCollection<Material>>> {
    private static final Type RAW_DESERIALIZED_MAP = new TypeToken<Map<String, Set<String>>>() {}.getType();
    private static final Pattern BLOCK_PATTERN = Pattern.compile("(.+):(\\d+)");

    private static final Logger LOGGER = JavaPlugin.getProvidingPlugin(InfectedPortalsBootstrap.class).getLogger();

    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    public Map<Material, ProbabilityCollection<Material>> deserialize(@NotNull final JsonElement json, @NotNull final Type typeOfT,
                                                               @NotNull final JsonDeserializationContext context) {
        final ImmutableMap.Builder<Material, ProbabilityCollection<Material>> replacementsBuilder = ImmutableMap.builder();

        for (final Map.Entry<String, Set<String>> entry : ((Map<String, Set<String>>) context.deserialize(json, RAW_DESERIALIZED_MAP)).entrySet()) {
            final Material material = parseMaterial(entry.getKey());
            if (material == null) continue;
            final ProbabilityCollection<Material> replacements = new ProbabilityCollection<>();

            for (final String replacement : entry.getValue()) {
                final Matcher matcher = BLOCK_PATTERN.matcher(replacement);

                if (!matcher.matches()) {
                    LOGGER.warning(replacement + " is an invalid replacement.");
                    continue;
                }

                final Material replacementMaterial = parseMaterial(matcher.group(1));
                if (replacementMaterial == null) continue;
                replacements.add(replacementMaterial, Integer.parseInt(matcher.group(2)));
            }

            replacementsBuilder.put(material, replacements);
        }

        return replacementsBuilder.build();
    }

    @Nullable
    private static Material parseMaterial(@NotNull final String material) {
        try {
            return Material.valueOf(material.toUpperCase());
        } catch (final Exception exception) {
            LOGGER.warning(material + " is not a valid material.");
        }

        return null;
    }
}
