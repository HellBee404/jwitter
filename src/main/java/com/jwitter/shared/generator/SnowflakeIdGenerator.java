package com.jwitter.shared.generator;

import org.springframework.beans.factory.annotation.Value;

import java.util.concurrent.atomic.AtomicLong;

public class SnowflakeIdGenerator {
    // Wed Jan 01 03:00:00 MSK 2025
    private static final long EPOCH = 1735689600000L;

    private static final int WORKER_ID_BITS = 5;

    private static final int SEQUENCE_BITS = 12;

    @Value("${app.worker-id}")
    private final int workerId;
    private final AtomicLong lastTimeStamp = new AtomicLong(-1L);
    private final AtomicLong sequence = new AtomicLong(0L);

    public SnowflakeIdGenerator(int workerId) {
        if (workerId < 0 || workerId >= (1 << WORKER_ID_BITS)) {
            throw new IllegalArgumentException("Worker ID must be between 0 and 31");
        }
        this.workerId = workerId;
    }

    public synchronized String generate() {
        long currentTimeStamp = System.currentTimeMillis() - EPOCH;

        if (currentTimeStamp < lastTimeStamp.get()) {
            throw new IllegalStateException("Clock moved backwards!");
        }

        if (currentTimeStamp == lastTimeStamp.get()) {
            sequence.set((sequence.get() + 1) & ((1 << SEQUENCE_BITS) - 1));
            if (sequence.get() == 0) {
                currentTimeStamp = waitNextMillis(currentTimeStamp);
            }
        } else {
            sequence.set(0L);
        }

        lastTimeStamp.set(currentTimeStamp);

        long id = (currentTimeStamp << (WORKER_ID_BITS + SEQUENCE_BITS))
                | ((long) workerId << SEQUENCE_BITS)
                | sequence.get();

        return String.valueOf(id);
    }

    private long waitNextMillis(long currentTimeStamp) {
        long timestamp = System.currentTimeMillis() - EPOCH;
        while (timestamp <= currentTimeStamp) {
            timestamp = System.currentTimeMillis() - EPOCH;
        }
        return timestamp;
    }

}

