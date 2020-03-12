package com.podo.climb.Utils;

import java.util.Random;

public class IdGenerator {

    private static final Random RANDOM = new Random();

    private static final long EPOCH_MILLIS = 1262304000000L;

    private static final int TIMESTAMP_SHIFT = 23;

    private static final int MAX_RANDOM = 0x800000;

    public static long generate() {
        long time = System.currentTimeMillis() - EPOCH_MILLIS;

        return (time << TIMESTAMP_SHIFT) + RANDOM.nextInt(MAX_RANDOM);
    }
}
