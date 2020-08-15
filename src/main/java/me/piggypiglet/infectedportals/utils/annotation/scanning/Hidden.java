package me.piggypiglet.infectedportals.utils.annotation.scanning;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// ------------------------------
// Copyright (c) PiggyPiglet 2020
// https://www.piggypiglet.me
// ------------------------------
@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME)
public @interface Hidden {
}
