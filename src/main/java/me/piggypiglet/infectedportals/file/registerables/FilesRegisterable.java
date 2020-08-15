package me.piggypiglet.infectedportals.file.registerables;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import me.piggypiglet.infectedportals.bootstrap.framework.Registerable;
import me.piggypiglet.infectedportals.file.FileManager;
import me.piggypiglet.infectedportals.file.annotations.File;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class FilesRegisterable extends Registerable {
    private final Set<Class<?>> fileClasses;
    private final FileManager fileManager;

    @Inject
    public FilesRegisterable(@NotNull @Named("files") final Set<Class<?>> fileClasses, @NotNull final FileManager fileManager) {
        this.fileClasses = fileClasses;
        this.fileManager = fileManager;
    }

    @Override
    protected void execute() {
        for (final Class<?> clazz : fileClasses) {
            final File data = clazz.getAnnotation(File.class);
            fileManager.loadFile(clazz, data.internalPath(), data.externalPath());
        }
    }
}
