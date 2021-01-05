package com.boydti.fawe.bukkit.adapter.v16.r3.wrappers;

import it.unimi.dsi.fastutil.shorts.ShortSet;
import net.minecraft.server.v1_16_R3.PlayerChunk;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

/**
 * Type-safe reflective wrapper for {@link PlayerChunkBlock}
 */
@SuppressWarnings("all")
public final class PlayerChunkWrapper {

    private static final Field fieldDirty;
    private static final Field fieldDirtyBlocks;

    static {
        try {
            fieldDirty = PlayerChunk.class.getDeclaredField("p");
            fieldDirty.setAccessible(true);
            fieldDirtyBlocks = PlayerChunk.class.getDeclaredField("dirtyBlocks");
            fieldDirtyBlocks.setAccessible(true);
        } catch (final RuntimeException e) {
            throw e;
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private final PlayerChunk playerChunk;

    private PlayerChunkWrapper(final PlayerChunk playerChunk) {
        this.playerChunk = playerChunk;
    }

    /**
     * Create a new wrapper
     *
     * @param playerChunk Chunk to wrap
     * @return Wrapped instance
     */
    public static @Nonnull PlayerChunkWrapper of(final @Nonnull PlayerChunk playerChunk) {
        return new PlayerChunkWrapper(playerChunk);
    }

    /**
     * Check if the chunk is dirty
     *
     * @return Whether or not the chunk is dirty
     */
    public boolean isDirty() {
        return this.accessSafely(() -> (boolean) fieldDirty.get(this.playerChunk));
    }

    /**
     * Set the chunk dirty status
     *
     * @param dirty Whether or not the chunk is dirty
     * @return {@code this}
     */
    public @Nonnull PlayerChunkWrapper setDirty(final boolean dirty) {
        this.setSafely(fieldDirty, dirty);
        return this;
    }

    /**
     * Get the dirty blocks
     *
     * @return Dirty blocks
     */
    public @Nonnull ShortSet[] getDirtyBlocks() {
        return this.accessSafely(() -> (ShortSet[]) fieldDirtyBlocks.get(this.playerChunk));
    }

    /**
     * Set the dirty blocks
     *
     * @param dirtyBlocks New dirty blocks
     * @return {@code this}
     */
    public @Nonnull PlayerChunkWrapper setDirtyBlocks(final @Nonnull ShortSet[] dirtyBlocks) {
        this.setSafely(fieldDirtyBlocks, dirtyBlocks);
        return this;
    }

    private <T> T accessSafely(final SafeAccessor<T> accessor) {
        try {
            return accessor.get();
        } catch (final Throwable e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> void setSafely(final Field field, final T value) {
        try {
            field.set(this, value);
        } catch (final Throwable e) {
            e.printStackTrace();
        }
    }

    private interface SafeAccessor<T> {

        T get() throws Throwable;

    }

}
