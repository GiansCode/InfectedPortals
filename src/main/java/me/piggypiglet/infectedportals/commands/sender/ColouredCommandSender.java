package me.piggypiglet.infectedportals.commands.sender;

import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Set;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
// ------------------------------
public final class ColouredCommandSender implements CommandSender {
    private final CommandSender composition;

    public ColouredCommandSender(@NotNull final CommandSender composition) {
        this.composition = composition;
    }

    @Override
    public void sendMessage(@NotNull final String message) {
        composition.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    @Override
    public void sendMessage(@NotNull final String[] messages) {
        Arrays.stream(messages).forEach(this::sendMessage);
    }

    @NotNull
    @Override
    public Server getServer() {
        return composition.getServer();
    }

    @NotNull
    @Override
    public String getName() {
        return composition.getName();
    }

    @NotNull
    @Override
    public Spigot spigot() {
        return composition.spigot();
    }

    @Override
    public boolean isPermissionSet(@NotNull final String name) {
        return composition.isPermissionSet(name);
    }

    @Override
    public boolean isPermissionSet(@NotNull final Permission perm) {
        return composition.isPermissionSet(perm);
    }

    @Override
    public boolean hasPermission(@NotNull final String name) {
        return composition.hasPermission(name);
    }

    @Override
    public boolean hasPermission(@NotNull final Permission perm) {
        return composition.hasPermission(perm);
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull final Plugin plugin, @NotNull final String name,
                                              final boolean value) {
        return composition.addAttachment(plugin, name, value);
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull final Plugin plugin) {
        return composition.addAttachment(plugin);
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull final Plugin plugin, @NotNull final String name,
                                              final boolean value, final int ticks) {
        return composition.addAttachment(plugin, name, value, ticks);
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull final Plugin plugin, final int ticks) {
        return composition.addAttachment(plugin, ticks);
    }

    @Override
    public void removeAttachment(@NotNull final PermissionAttachment attachment) {
        composition.removeAttachment(attachment);
    }

    @Override
    public void recalculatePermissions() {
        composition.recalculatePermissions();
    }

    @NotNull
    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return composition.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return composition.isOp();
    }

    @Override
    public void setOp(final boolean value) {
        composition.setOp(value);
    }

    @NotNull
    public CommandSender getComposition() {
        return composition;
    }
}
