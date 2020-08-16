package me.piggypiglet.infectedportals.file.objects.deserialization;

import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import me.piggypiglet.infectedportals.bootstrap.InfectedPortalsBootstrap;
import me.piggypiglet.infectedportals.utils.collection.ProbabilityCollection;
import me.piggypiglet.infectedportals.utils.material.MaterialUtils;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class ReplacementsDeserializer implements JsonDeserializer<Table<Material, Integer, ProbabilityCollection<Material>>> {
    public static final Material PARENT;

    static {
        Material parent;

        try {
            parent = Material.class.getDeclaredConstructor(int.class).newInstance(-1);
        } catch (final Exception exception) {
            parent = Material.ACACIA_BOAT;
        }

        PARENT = parent;
    }

    private static final Type RAW_DESERIALIZED_MAP = new TypeToken<Map<String, Map<String, Set<String>>>>() {}.getType();
    private static final Pattern COLLECTION_DELIMITER = Pattern.compile(",");
    private static final Pattern BLOCK_PATTERN = Pattern.compile("(.+):(\\d+)");

    private static final Logger LOGGER = JavaPlugin.getProvidingPlugin(InfectedPortalsBootstrap.class).getLogger();

    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    public Table<Material, Integer, ProbabilityCollection<Material>> deserialize(@NotNull final JsonElement json, @NotNull final Type typeOfT,
                                                                                 @NotNull final JsonDeserializationContext context) {
        final ImmutableTable.Builder<Material, Integer, ProbabilityCollection<Material>> replacementsBuilder = ImmutableTable.builder();

        for (final Map.Entry<String, Map<String, Set<String>>> materialEntry : ((Map<String, Map<String, Set<String>>>) context.deserialize(json, RAW_DESERIALIZED_MAP)).entrySet()) {
            final Set<Material> materials = Arrays.stream(COLLECTION_DELIMITER.split(materialEntry.getKey()))
                    .map(ReplacementsDeserializer::parseMaterial)
                    .collect(Collectors.toSet());
            if (materials.isEmpty()) continue;

            for (final Map.Entry<String, Set<String>> layerEntry : materialEntry.getValue().entrySet()) {
                final Set<Integer> layers = Arrays.stream(COLLECTION_DELIMITER.split(layerEntry.getKey()))
                        .map(Integer::parseInt)
                        .collect(Collectors.toSet());
                final ProbabilityCollection<Material> replacements = new ProbabilityCollection<>();

                for (final String replacement : layerEntry.getValue()) {
                    final Matcher matcher = BLOCK_PATTERN.matcher(replacement);

                    if (!matcher.matches()) {
                        LOGGER.warning(replacement + " is an invalid replacement.");
                        continue;
                    }

                    final Material replacementMaterial = parseMaterial(matcher.group(1));
                    if (replacementMaterial == null) continue;
                    replacements.add(replacementMaterial, Integer.parseInt(matcher.group(2)));
                }

                materials.forEach(material -> layers.forEach(layer -> replacementsBuilder.put(material, layer, replacements)));
            }
        }

        return replacementsBuilder.build();
    }

    @Nullable
    private static Material parseMaterial(@NotNull final String material) {
        return material.equalsIgnoreCase("$parent") ? PARENT : MaterialUtils.parseMaterial(material);
    }
}
