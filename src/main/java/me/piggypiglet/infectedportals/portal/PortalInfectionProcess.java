package me.piggypiglet.infectedportals.portal;

import com.google.inject.Inject;
import me.piggypiglet.infectedportals.file.objects.Config;
import me.piggypiglet.infectedportals.portal.objects.InfectedBlock;
import me.piggypiglet.infectedportals.task.Task;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class PortalInfectionProcess {
    private final Task task;
    private final Config config;

    @Inject
    public PortalInfectionProcess(@NotNull final Task task, @NotNull final Config config) {
        this.task = task;
        this.config = config;
    }

    public void infect(@NotNull final AtomicInteger iteration, @NotNull final List<Set<InfectedBlock>> infectedBlocks) {
            final Long[] timePerLayer = config.getTimePerLayer();
            if (iteration.get() == config.getRadius()) return;

            final ThreadLocalRandom random = ThreadLocalRandom.current();

            infectedBlocks.get(iteration.getAndIncrement()).forEach(block -> task.syncLater(
                    () -> block.getBlock().setType(block.getTo()),
                    random.nextLong(timePerLayer[0], timePerLayer[1])
            ));

            task.syncLater(() -> infect(iteration, infectedBlocks), timePerLayer[1]);
    }
}
