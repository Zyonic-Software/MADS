package com.zyonicsoftware.minereaper.mads.util;

public class MathUtil {
    public static boolean isInteger(final double d) {
        return ((d == Math.floor(d)) && !Double.isInfinite(d));
    }

    public static boolean isEven(final double d) {
        return ((d % 2 == 0) && !Double.isInfinite(d));
    }
}
