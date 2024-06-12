package com.Capstone.Project.global.generator;

import java.util.Random;

public class CodeGenerator {
    private static final Random RANDOM = new Random();
    private static final String CODE_PATTERN = "0123456789abcdefghijklmnopqrstuvwxyz";

    public static String generateDigitCode(int digitCount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i< digitCount; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }



    public static String generateHexCode(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i< length; i++) {
            int idx = RANDOM.nextInt(CODE_PATTERN.length());
            sb.append(CODE_PATTERN.charAt(idx));
        }
        return sb.toString();
    }
}
