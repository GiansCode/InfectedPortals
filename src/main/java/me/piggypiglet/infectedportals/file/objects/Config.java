package me.piggypiglet.infectedportals.file.objects;

import com.google.common.collect.Table;
import com.google.gson.annotations.JsonAdapter;
import com.google.inject.Singleton;
import me.piggypiglet.infectedportals.file.annotations.File;
import me.piggypiglet.infectedportals.file.objects.deserialization.IntegerRangeDeserializer;
import me.piggypiglet.infectedportals.file.objects.deserialization.LootAdapter;
import me.piggypiglet.infectedportals.file.objects.deserialization.ReplacementsDeserializer;
import me.piggypiglet.infectedportals.file.objects.deserialization.TimeRangeDeserializer;
import me.piggypiglet.infectedportals.utils.collection.ProbabilityCollection;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
@Singleton
@File(
        internalPath = "/config.yml",
        externalPath = "config.yml"
)
public final class Config {
    private int radius;
    @JsonAdapter(TimeRangeDeserializer.class) private Long[] timePerLayer;
    private Set<String> worlds;
    @JsonAdapter(ReplacementsDeserializer.class) private Table<Material, Integer, ProbabilityCollection<Material>> replacements;
    private int lootChance;
    @JsonAdapter(IntegerRangeDeserializer.class) private Integer[] itemAmount;
    @JsonAdapter(LootAdapter.class) private ProbabilityCollection<ItemStack> loot;

    public int getRadius() {
        return radius;
    }

    @NotNull
    public Long[] getTimePerLayer() {
        return timePerLayer;
    }

    @NotNull
    public Set<String> getWorlds() {
        return worlds;
    }

    @NotNull
    public Table<Material, Integer, ProbabilityCollection<Material>> getReplacements() {
        return replacements;
    }

    public int getLootChance() {
        return lootChance;
    }

    @NotNull
    public Integer[] getItemAmount() {
        return itemAmount;
    }

    @NotNull
    public ProbabilityCollection<ItemStack> getLoot() {
        return loot;
    }
}
