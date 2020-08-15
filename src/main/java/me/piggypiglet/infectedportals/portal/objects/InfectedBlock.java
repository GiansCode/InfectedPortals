package me.piggypiglet.infectedportals.portal.objects;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class InfectedBlock {
    private final Block block;
    private final Material to;
    private final BlockFace from;
    private final int iteration;

    public InfectedBlock(@NotNull final Block block, @NotNull final Material to,
                         @NotNull final BlockFace from, final int iteration) {
        this.block = block;
        this.to = to;
        this.from = from;
        this.iteration = iteration;
    }

    @NotNull
    public Block getBlock() {
        return block;
    }

    @NotNull
    public Material getTo() {
        return to;
    }

    @NotNull
    public BlockFace getFrom() {
        return from;
    }

    public int getIteration() {
        return iteration;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final InfectedBlock that = (InfectedBlock) o;
        return block.getLocation().equals(that.block.getLocation());
    }

    @Override
    public int hashCode() {
        return block.getLocation().hashCode();
    }
}
