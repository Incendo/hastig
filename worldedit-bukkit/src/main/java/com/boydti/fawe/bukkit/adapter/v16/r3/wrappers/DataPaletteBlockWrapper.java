package com.boydti.fawe.bukkit.adapter.v16.r3.wrappers;

import com.boydti.fawe.util.ReflectionUtils;
import net.minecraft.server.v1_16_R3.DataBits;
import net.minecraft.server.v1_16_R3.DataPalette;
import net.minecraft.server.v1_16_R3.DataPaletteBlock;
import net.minecraft.server.v1_16_R3.IBlockData;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Type-safe reflective wrapper for {@link DataPaletteBlock}
 */
@SuppressWarnings("all")
public final class DataPaletteBlockWrapper {

    private static final Field fieldBits;
    private  static final Field fieldPalette;
    private static final Field fieldSize;
    private static final Field fieldLock;

    static {
        try {
            fieldSize = DataPaletteBlock.class.getDeclaredField("i");
            fieldSize.setAccessible(true);
            fieldBits = DataPaletteBlock.class.getDeclaredField("a");
            fieldBits.setAccessible(true);
            fieldPalette = DataPaletteBlock.class.getDeclaredField("h");
            fieldPalette.setAccessible(true);
            // TODO: This is very very very stupid...
            Field tmp = DataPaletteBlock.class.getDeclaredField("j");
            ReflectionUtils.setAccessibleNonFinal(tmp);
            fieldLock = tmp;
            fieldLock.setAccessible(true);
        } catch (final RuntimeException e) {
            throw e;
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private final DataPaletteBlock<IBlockData> dataPaletteBlock;

    private DataPaletteBlockWrapper(DataPaletteBlock<IBlockData> dataPaletteBlock) {
        this.dataPaletteBlock = dataPaletteBlock;
    }

    /**
     * Create a new wrapper
     *
     * @param dataPaletteBlock Block to wrap
     * @return Wrapped instance
     */
    public static @Nonnull DataPaletteBlockWrapper of(final @Nonnull DataPaletteBlock<IBlockData> dataPaletteBlock) {
        return new DataPaletteBlockWrapper(dataPaletteBlock);
    }

    /**
     * Get the sisze of the data palette
     *
     * @return Data palette block size
     */
    public int getSize() {
        return this.accessSafely(() -> (int) fieldSize.get(this.dataPaletteBlock));
    }

    /**
     * Set the size of the data palette
     *
     * @param size New size
     * @return {@code this}
     */
    public @Nonnull DataPaletteBlockWrapper setSize(final int size) {
        this.setSafely(fieldSize, size);
        return this;
    }

    /**
     * Get the {@link DataBits} instance from the wrapped data palette
     *
     * @return Palette bits
     */
    public @Nonnull DataBits getBits() {
        return this.accessSafely(() -> (DataBits) fieldBits.get(this.dataPaletteBlock));
    }

    /**
     * Set the bits in the wrapped data palette
     *
     * @param bits New bits
     * @return {@code this}
     */
    public @Nonnull DataPaletteBlockWrapper setBits(final @Nonnull DataBits bits) {
        this.setSafely(fieldBits, bits);
        return this;
    }

    /**
     * Get the {@link DataPalette} from the wrapped data palette
     *
     * @return Data palette
     */
    public @Nonnull DataPalette<IBlockData> getDataPalette() {
        return this.accessSafely(() -> (DataPalette<IBlockData>) fieldPalette.get(this.dataPaletteBlock));
    }

    /**
     * Set the {@link DataPalette} instance in the wrapped data palette
     *
     * @param dataPalette New data palette
     * @return {@code this}
     */
    public @Nonnull DataPaletteBlockWrapper setDataPalette(final @Nonnull DataPalette<IBlockData> dataPalette) {
        this.setSafely(fieldPalette, dataPalette);
        return this;
    }

    /**
     * Get the lock
     *
     * @return Lock
     */
    public @Nonnull ReentrantLock getLock() {
        return this.accessSafely(() -> (ReentrantLock) fieldLock.get(this.dataPaletteBlock));
    }

    /**
     * Set the lock
     *
     * @param lock New lock
     * @return {@code this}
     */
    public @Nonnull DataPaletteBlockWrapper setLock(final @Nonnull ReentrantLock lock) {
        this.setSafely(fieldLock, lock);
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
