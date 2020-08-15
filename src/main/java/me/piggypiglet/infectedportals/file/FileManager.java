package me.piggypiglet.infectedportals.file;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.piggypiglet.infectedportals.file.deserialization.FileDeserializer;
import me.piggypiglet.infectedportals.file.deserialization.YamlFileDeserializer;
import me.piggypiglet.infectedportals.file.exceptions.FileLoadException;
import me.piggypiglet.infectedportals.utils.file.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
@Singleton
public final class FileManager {
    private static final FileDeserializer DESERIALIZER = new YamlFileDeserializer();
    private static final Type MAP = new TypeToken<Map<String, Object>>() {}.getType();

    private final String dataDirectory;
    private final Gson gson;

    @Inject
    public FileManager(@NotNull final JavaPlugin main, @NotNull @Named("files") final Map<Class<?>, Object> fileObjects) {
        this.dataDirectory = main.getDataFolder().getPath();

        final AtomicReference<GsonBuilder> builder = new AtomicReference<>(new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES));

        fileObjects.forEach((clazz, instance) -> builder.set(builder.get()
                .registerTypeAdapter(clazz, instanceCreator(instance))));

        gson = builder.get().create();
    }

    @NotNull
    private static <T> InstanceCreator<T> instanceCreator(@NotNull final T instance) {
        return type -> instance;
    }

    public void loadFile(@NotNull final Class<?> file, @NotNull final String internalPath,
                         @NotNull final String externalPath) {
        try {
            gson.fromJson(gson.toJson(DESERIALIZER.deserialize(FileUtils.readFile(createFile(internalPath, externalPath)))), file);
        } catch (final Exception exception) {
            throw new FileLoadException(exception);
        }
    }

    @NotNull
    private File createFile(@NotNull final String internalPath, @NotNull final String externalPath) throws IOException {
        return FileUtils.createFile(internalPath, dataDirectory + '/' + externalPath);
    }
}
