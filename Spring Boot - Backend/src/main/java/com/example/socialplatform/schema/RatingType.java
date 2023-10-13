package com.example.socialplatform.schema;

import com.example.socialplatform.exception.SocialPlatformException;

import java.util.Arrays;

public enum RatingType {
    LIKE(1), DISLIKE(-1),
    ;

    private int direction;

    RatingType(int direction) {
    }

    public static RatingType lookup(Integer direction) {
        return Arrays.stream(RatingType.values())
                .filter(value -> value.getDirection().equals(direction))
                .findAny()
                .orElseThrow(() -> new SocialPlatformException("Nie znaleziono oceny"));
    }

    public Integer getDirection() {
        return direction;
    }
}
