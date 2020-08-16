package me.piggypiglet.infectedportals.commands.implementations;

import me.piggypiglet.infectedportals.commands.framework.Command;
import org.bukkit.NamespacedKey;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class TestCommand extends Command {
    public TestCommand() {
        super("test");
    }

    @Override
    public boolean execute(final @NotNull CommandSender sender, final @NotNull String[] args) {
        sender.sendMessage(Enchantment.getByKey(NamespacedKey.minecraft("sharpness")).toString());
//        sender.sendMessage(Enchantment.DAMAGE_ALL.getKey().getKey());
        return true;
    }
}
