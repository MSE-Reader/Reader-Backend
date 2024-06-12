package com.Capstone.Project.domain.user.model;

import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum Gender {

    MALE("남성"),
    FEMALE("여성");

    private final String gender;

    Gender(String gender) {
        this.gender = gender;
    }

    private static final Map<String, Gender> BY_LABEL =
            Stream.of(values()).collect(Collectors.toMap(Gender::getGender, e -> e));

    public static Gender of(String gender) { return BY_LABEL.get(gender);}
}
