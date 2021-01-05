package com.boydti.fawe.bukkit.adapter.v16.r3.wrappers;

import net.minecraft.server.v1_16_R3.BiomeBase;
import net.minecraft.server.v1_16_R3.BiomeStorage;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

/**
 * Type-safe reflective wrapper for {@link BiomeStorage}
 */
public final class BiomeStorageWrapper {

    private static final Field fieldBiomeArray;

    static {
        try {
            fieldBiomeArray = BiomeStorage.class.getDeclaredField("h");
            fieldBiomeArray.setAccessible(true);
        } catch (final RuntimeException e) {
            throw e;
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private final BiomeStorage biomeStorage;

    private BiomeStorageWrapper(final @Nonnull BiomeStorage biomeStorage) {
        this.biomeStorage = biomeStorage;
    }

    /**
     * Create a new wrapper
     *
     * @param biomeStorage Biome storage to wrap
     * @return Wrapped instance
     */
    public static @Nonnull BiomeStorageWrapper of(final @Nonnull BiomeStorage biomeStorage) {
        return new BiomeStorageWrapper(biomeStorage);
    }

    /**
     * Get the internal biome array
     *
     * @return Biome array
     */
    public @Nonnull BiomeBase[] getBiomeArray() {
        try {
            return (BiomeBase[]) fieldBiomeArray.get(this.biomeStorage);
        } catch (final Throwable t) {
            throw new RuntimeException(t);
        }
    }

}
