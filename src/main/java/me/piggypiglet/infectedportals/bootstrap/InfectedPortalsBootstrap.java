package me.piggypiglet.infectedportals.bootstrap;

import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import me.piggypiglet.infectedportals.bootstrap.framework.Registerable;
import me.piggypiglet.infectedportals.commands.registerable.CommandHandlerRegisterable;
import me.piggypiglet.infectedportals.commands.registerable.CommandsRegisterable;
import me.piggypiglet.infectedportals.events.EventsRegisterable;
import me.piggypiglet.infectedportals.file.registerables.FileObjectsRegisterable;
import me.piggypiglet.infectedportals.file.registerables.FilesRegisterable;
import me.piggypiglet.infectedportals.guice.ExceptionalInjector;
import me.piggypiglet.infectedportals.guice.modules.DynamicModule;
import me.piggypiglet.infectedportals.guice.modules.InitialModule;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class InfectedPortalsBootstrap extends JavaPlugin {
    private static final List<Class<? extends Registerable>> REGISTERABLES = Lists.newArrayList(
            FileObjectsRegisterable.class,
            FilesRegisterable.class,

            CommandHandlerRegisterable.class,
            CommandsRegisterable.class,

            EventsRegisterable.class
    );

    @Override
    public void onEnable() {
        Injector injector = new ExceptionalInjector(Guice.createInjector(new InitialModule(this)));

        for (final Class<? extends Registerable> registerableClass : REGISTERABLES) {
            final Registerable registerable = injector.getInstance(registerableClass);
            registerable.run(injector);

            if (!registerable.getBindings().isEmpty() || registerable.getStaticInjections().length > 0) {
                injector = injector.createChildInjector(new DynamicModule(
                        registerable.getBindings(),
                        registerable.getStaticInjections()
                ));
            }
        }
    }
}
