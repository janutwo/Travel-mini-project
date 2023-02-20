package com.people.travel.core.entity.base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coordinate {

    private double posX;
    private double posY;

    public Coordinate(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }
}
