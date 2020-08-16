package me.piggypiglet.infectedportals.portal;

import com.google.inject.Inject;
import me.piggypiglet.infectedportals.file.objects.Config;
import me.piggypiglet.infectedportals.portal.objects.InfectedBlock;
import me.piggypiglet.infectedportals.task.Task;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class PortalLootPlacer {
    private final Config config;
    private final Task task;

    @Inject
    public PortalLootPlacer(@NotNull final Config config, @NotNull final Task task) {
        this.config = config;
        this.task = task;
    }

    public void place(@NotNull final Set<InfectedBlock> blocks) {
        if (ThreadLocalRandom.current().nextInt(0, 1000) > config.getLootChance()) return;

        final Optional<Block> optionalBlock = blocks.stream()
                .map(InfectedBlock::getBlock)
                .map(block -> block.getRelative(BlockFace.UP))
                .filter(block -> block.getType() == Material.AIR || block.getType() == Material.CAVE_AIR)
                .findAny();

        if (!optionalBlock.isPresent()) return;

        final Block block = optionalBlock.get();

        task.syncLater(() -> {
            block.setType(Material.CHEST);

            final Chest chest = (Chest) block.getState();
            final List<Integer> ints = IntStream.range(0, 27).boxed().collect(Collectors.toList());
            Collections.shuffle(ints);

            IntStream.rangeClosed(0, ThreadLocalRandom.current().nextInt(config.getItemAmount()[0], config.getItemAmount()[1]))
                    .map(ints::get)
                    .forEach(i -> chest.getBlockInventory().setItem(i, config.getLoot().get()));
        }, config.getTimePerLayer()[1] * config.getRadius());
    }
}
