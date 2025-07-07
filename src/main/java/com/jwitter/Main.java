package com.jwitter;

import com.jwitter.shared.generator.SnowflakeIdGenerator;

public class Main {
    public static void main(String[] args) {
        SnowflakeIdGenerator generator = new SnowflakeIdGenerator(1);

        String id = generator.generate();
        System.out.println(id);
    }
}
