package com.boydti.fawe.bukkit.adapter.v16.r3.wrappers;

import net.minecraft.server.v1_16_R3.DataBits;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;

/**
 * Type-safe reflective wrapper for {@link DataBits}
 */
public final class DataBitsWrapper {

    private static final Field fieldBitsPerEntry;

    static {
        try {
            fieldBitsPerEntry = DataBits.class.getDeclaredField("c");
            fieldBitsPerEntry.setAccessible(true);
        } catch (final RuntimeException e) {
            throw e;
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private final DataBits dataBits;

    private DataBitsWrapper(final @Nonnull DataBits dataBits) {
        this.dataBits = dataBits;
    }

    /**
     * Create a new wrapper
     *
     * @param dataBits DataBits to wrap
     * @return Wrapped instance
     */
    public static @Nonnull DataBitsWrapper of(final @Nonnull DataBits dataBits) {
        return new DataBitsWrapper(dataBits);
    }

    /**
     * Get the bits per entry of the wrapped {@link DataBits} instance
     *
     * @return Bits per entry
     */
    public int getBitsPerEntry() {
        try {
            return (int) fieldBitsPerEntry.get(this.dataBits);
        } catch (final Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }

}
