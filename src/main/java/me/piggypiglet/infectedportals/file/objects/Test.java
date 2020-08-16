package me.piggypiglet.infectedportals.file.objects;

import org.bukkit.loot.LootTable;
import org.bukkit.loot.Lootable;
import org.jetbrains.annotations.Nullable;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class Test implements Lootable {
    @Override
    public void setLootTable(@Nullable final LootTable table) {

    }

    @Override
    public @Nullable LootTable getLootTable() {
        return null;
    }

    @Override
    public void setSeed(final long seed) {

    }

    @Override
    public long getSeed() {
        return 0;
    }
}
