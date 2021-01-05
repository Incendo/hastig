package com.boydti.fawe.bukkit.adapter.v16.r3.wrappers;

import net.minecraft.server.v1_16_R3.ChunkSection;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

/**
 * Type-safe reflective wrapper for {@link ChunkSection}
 */
@SuppressWarnings("all")
public final class ChunkSectionWrapper {

    private static final Field fieldFluidCount;
    private static final Field fieldTickingBlockCount;
    private static final Field fieldNonEmptyBlockCount;

    static {
        try {
            fieldFluidCount = ChunkSection.class.getDeclaredField("e");
            fieldFluidCount.setAccessible(true);
            fieldTickingBlockCount = ChunkSection.class.getDeclaredField("tickingBlockCount");
            fieldTickingBlockCount.setAccessible(true);
            fieldNonEmptyBlockCount = ChunkSection.class.getDeclaredField("nonEmptyBlockCount");
            fieldNonEmptyBlockCount.setAccessible(true);
        } catch (final RuntimeException e) {
            throw e;
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private final ChunkSection chunkSection;

    private ChunkSectionWrapper(final @Nonnull ChunkSection chunkSection) {
        this.chunkSection = chunkSection;
    }

    /**
     * Create a new wrapper
     *
     * @param chunkSection Chunks section to wrap
     * @return Wrapped instance
     */
    public static @Nonnull ChunkSectionWrapper of(final @Nonnull ChunkSection chunkSection) {
        return new ChunkSectionWrapper(chunkSection);
    }

    /**
     * Get the fluid count
     *
     * @return Fluid count
     */
    public short getFluidCount() {
        return this.accessSafely(() -> (short) fieldFluidCount.get(this.chunkSection));
    }

    /**
     * Set the fluid count
     *
     * @param count New count
     * @return {@code this}
     */
    public @Nonnull ChunkSectionWrapper setFluidCount(final short count) {
        this.setSafely(fieldFluidCount, count);
        return this;
    }

    /**
     * Get the ticking block count
     *
     * @return Ticking block count
     */
    public short getTickingBlockCountCount() {
        return this.accessSafely(() -> (short) fieldTickingBlockCount.get(this.chunkSection));
    }

    /**
     * Set the ticking block count
     *
     * @param count New count
     * @return {@code this}
     */
    public @Nonnull ChunkSectionWrapper setTickingBlockCount(final short count) {
        this.setSafely(fieldTickingBlockCount, count);
        return this;
    }

    /**
     * Get the non empty block count
     *
     * @return Ticking block count
     */
    public short setNonEmptyBlockCountCount() {
        return this.accessSafely(() -> (short) fieldNonEmptyBlockCount.get(this.chunkSection));
    }

    /**
     * Set the ticking block count
     *
     * @param count New count
     * @return {@code this}
     */
    public @Nonnull ChunkSectionWrapper setNonEmptyBlockCount(final short count) {
        this.setSafely(fieldNonEmptyBlockCount, count);
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
