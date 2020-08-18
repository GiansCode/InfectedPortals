package me.piggypiglet.infectedportals.file.objects.deserialization;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import me.piggypiglet.infectedportals.utils.collection.ProbabilityCollection;
import me.piggypiglet.infectedportals.utils.material.MaterialUtils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class LootAdapter implements JsonDeserializer<ProbabilityCollection<ItemStack>> {
    private static final Type RAW_DESERIALIZED_SET = new TypeToken<Set<Map<String, Object>>>() {}.getType();

    @SuppressWarnings("unchecked")
    @NotNull
    @Override
    public ProbabilityCollection<ItemStack> deserialize(@NotNull final JsonElement json, @NotNull final Type typeOfT,
                                                           @NotNull final JsonDeserializationContext context) {
        final ProbabilityCollection<ItemStack> result = new ProbabilityCollection<>();

        ((Set<Map<String, Object>>) context.deserialize(json, RAW_DESERIALIZED_SET)).forEach(map -> {
            final Material material = MaterialUtils.parseMaterial((String) map.get("material"));
            final int amount = (int) (double) map.get("amount");
            final int chance = (int) (double) map.get("chance");

            final ItemStack item = new ItemStack(Objects.requireNonNull(material), amount);
            final ItemMeta meta = Objects.requireNonNull(item.getItemMeta());

            final Map<String, Object> deserializedMeta = (Map<String, Object>) map.get("meta");

            Optional.ofNullable(deserializedMeta.get("name")).map(String.class::cast).ifPresent(meta::setDisplayName);
            Optional.ofNullable(deserializedMeta.get("lore")).map(obj -> (List<String>) obj).ifPresent(meta::setLore);
            Optional.ofNullable(deserializedMeta.get("custom_model"))
                    .map(Double.class::cast)
                    .map(Double::intValue)
                    .ifPresent(meta::setCustomModelData);
            Optional.ofNullable(deserializedMeta.get("unbreakable")).map(Boolean.class::cast).ifPresent(meta::setUnbreakable);
            Optional.ofNullable(deserializedMeta.get("enchants"))
                    .map(obj -> (Map<String, Double>) obj)
                    .map(enchants -> enchants.entrySet().stream()
                            .collect(Collectors.toMap(entry -> Enchantment.getByKey(NamespacedKey.minecraft(entry.getKey())), Map.Entry::getValue)))
                    .ifPresent(enchants -> enchants.forEach((enchantment, level) -> meta.addEnchant(enchantment, level.intValue(), true)));
            Optional.ofNullable(deserializedMeta.get("item_flags"))
                    .map(obj -> (List<String>) obj)
                    .map(flags -> flags.stream().map(String::toUpperCase).map(ItemFlag::valueOf).toArray(ItemFlag[]::new))
                    .ifPresent(meta::addItemFlags);

            item.setItemMeta(meta);
            result.add(item, chance);
        });

        return result;
    }
}
