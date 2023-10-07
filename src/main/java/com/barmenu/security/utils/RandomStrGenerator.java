package com.barmenu.security.utils;

import java.util.UUID;

public class RandomStrGenerator {
    public static String generate() {
        UUID randomUUID = UUID.randomUUID();
        return randomUUID.toString().replaceAll("-", "");
    }
}
