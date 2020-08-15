package me.piggypiglet.infectedportals.portal;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.piggypiglet.infectedportals.file.objects.Config;
import me.piggypiglet.infectedportals.portal.objects.InfectedBlock;
import me.piggypiglet.infectedportals.task.Task;
import me.piggypiglet.infectedportals.utils.collection.ProbabilityCollection;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class PortalInfectionSender implements Listener {
    private static final Set<BlockFace> CONNECTED_BLOCKFACES = ImmutableSet.of(
            BlockFace.UP,
            BlockFace.DOWN,
            BlockFace.NORTH,
//            BlockFace.NORTH_EAST,
            BlockFace.EAST,
//            BlockFace.SOUTH_EAST,
            BlockFace.SOUTH,
//            BlockFace.SOUTH_WEST,
            BlockFace.WEST
//            BlockFace.NORTH_WEST
    );

    private final Map<BlockFace, BlockFace> OPPOSITE_FACES = ImmutableMap.<BlockFace, BlockFace>builder()
            .put(BlockFace.UP, BlockFace.DOWN)
            .put(BlockFace.DOWN, BlockFace.UP)
            .put(BlockFace.NORTH, BlockFace.SOUTH)
            .put(BlockFace.SOUTH, BlockFace.NORTH)
//            .put(BlockFace.NORTH_EAST, BlockFace.SOUTH_WEST)
//            .put(BlockFace.SOUTH_WEST, BlockFace.NORTH_EAST)
            .put(BlockFace.EAST, BlockFace.WEST)
            .put(BlockFace.WEST, BlockFace.EAST)
//            .put(BlockFace.SOUTH_EAST, BlockFace.NORTH_WEST)
//            .put(BlockFace.NORTH_WEST, BlockFace.SOUTH_EAST)
            .build();

    private final Config config;
    private final Task task;
    private final Predicate<InfectedBlock> blockFilter;

    @Inject
    public PortalInfectionSender(@NotNull final Config config, @NotNull final Task task) {
        this.config = config;
        this.task = task;
        this.blockFilter = block -> config.getIgnored().stream().noneMatch(block.getBlock().getType()::equals) &&
                config.getReplacements().containsKey(block.getBlock().getType());
    }

    @EventHandler
    public void onPortalCreate(@NotNull final PortalCreateEvent event) {
        final List<BlockState> states = event.getBlocks();

        final Optional<Integer> aBottomBlockY = states.stream()
                .min(Comparator.comparingInt(BlockState::getY))
                .map(BlockState::getY);

        if (!aBottomBlockY.isPresent()) return;

        final int bottomY = aBottomBlockY.get();
        final Stream<Block> bottomRow = states.stream()
                .filter(state -> state.getY() == bottomY)
                .map(state -> state.getBlock().getRelative(BlockFace.DOWN))
                .filter(block -> block.getType() == Material.OBSIDIAN);
        final Stream<Block> rest = states.stream()
                .map(BlockState::getBlock);

        final List<Set<InfectedBlock>> infectedBlocks = new ArrayList<>(config.getRadius());
        infectedBlocks.add(Stream.concat(bottomRow, rest)
                .flatMap(block -> CONNECTED_BLOCKFACES.stream()
                        .map(face -> createInfectedBlock(block, face, 0)))
                .filter(blockFilter)
                .collect(Collectors.toSet()));

        for (int i = 0; i < config.getRadius(); ++i) {
            final int iteration = i + 1;

            infectedBlocks.add(iteration, infectedBlocks.get(i).stream()
                    .flatMap(block -> CONNECTED_BLOCKFACES.stream()
                            .filter(face -> OPPOSITE_FACES.get(face) != block.getFrom())
                            .map(face -> createInfectedBlock(block.getBlock(), face, iteration)))
                    .filter(blockFilter)
                    .collect(Collectors.toSet()));
        }

        final Set<InfectedBlock> distinctlyMerged = infectedBlocks.stream()
                .flatMap(Set::stream)
                .collect(Collectors.toSet());
        infectedBlocks.clear();
        IntStream.rangeClosed(0, config.getRadius()).forEach(i -> infectedBlocks.add(new HashSet<>()));
        distinctlyMerged.forEach(block -> infectedBlocks.get(block.getIteration()).add(block));

        schedule(new AtomicInteger(0), infectedBlocks);
    }

    @NotNull
    private InfectedBlock createInfectedBlock(@NotNull final Block block, @NotNull final BlockFace face,
                                              final int iteration) {
        final Block to = block.getRelative(face);
        final ProbabilityCollection<Material> replacements = config.getReplacements().get(to.getType());
        final Material replacement;

        if (replacements == null) {
            replacement = to.getType();
        } else {
            replacement = replacements.get();
        }

        return new InfectedBlock(to, replacement, face, iteration);
    }

    private void schedule(@NotNull final AtomicInteger iteration, @NotNull final List<Set<InfectedBlock>> infectedBlocks) {
        final Long[] timePerLayer = config.getTimePerLayer();
        if (iteration.get() == config.getRadius()) return;

        final ThreadLocalRandom random = ThreadLocalRandom.current();

        infectedBlocks.get(iteration.getAndIncrement()).forEach(block -> task.syncLater(
                () -> block.getBlock().setType(block.getTo()),
                random.nextLong(timePerLayer[0], timePerLayer[1])
        ));

        task.syncLater(() -> schedule(iteration, infectedBlocks), timePerLayer[1]);
    }
}
