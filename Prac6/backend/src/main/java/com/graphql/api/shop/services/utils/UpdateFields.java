package com.graphql.api.shop.services.utils;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class UpdateFields {
    public static void updateField(@NotNull Object newObject, @NotNull Object lastObject) {
        Arrays.stream(newObject.getClass().getDeclaredFields()).filter(field -> {
            field.setAccessible(true);
            try {
                return field.get(newObject) != null;
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).forEach(field -> {
            field.setAccessible(true);
            try {
                field.set(lastObject, field.get(newObject));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
