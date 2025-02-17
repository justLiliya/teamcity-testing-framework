package com.example.teamcity.api.generators;

import org.apache.commons.lang3.RandomStringUtils;

public final class RandomData {
    private static final String TEST_PREFIX = "test_";
    public static final int MAX_LENGTH = 10;
    public static final int MIN_LENGTH = 1;

    public static String getString(String name, int countOfSimbols) {
        return name + RandomStringUtils.randomAlphabetic(countOfSimbols);
    }

    public static String getString(int countOfSimbols) {
        return TEST_PREFIX + RandomStringUtils.randomAlphabetic(countOfSimbols);
    }
}
