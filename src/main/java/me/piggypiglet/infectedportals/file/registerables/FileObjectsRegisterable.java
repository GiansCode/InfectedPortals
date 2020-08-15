package me.piggypiglet.infectedportals.file.registerables;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import me.piggypiglet.infectedportals.bootstrap.framework.Registerable;
import me.piggypiglet.infectedportals.file.annotations.File;
import me.piggypiglet.infectedportals.scanning.framework.Scanner;
import me.piggypiglet.infectedportals.utils.annotation.wrapper.AnnotationRules;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
public final class FileObjectsRegisterable extends Registerable {
    private static final Named FILES = Names.named("files");

    private final Scanner scanner;

    @Inject
    public FileObjectsRegisterable(@NotNull final Scanner scanner) {
        this.scanner = scanner;
    }

    @Override
    protected void execute(@NotNull final Injector injector) {
        final Set<Class<?>> fileClasses = scanner.getClassesAnnotatedWith(AnnotationRules.hasAnnotation(File.class));

        addBinding(new TypeLiteral<Set<Class<?>>>() {}, FILES, fileClasses);
        addBinding(new TypeLiteral<Map<Class<?>, Object>>() {}, FILES, fileClasses.stream()
                .collect(Collectors.toMap(clazz -> clazz, injector::getInstance)));
    }
}
