package com.people.travel.core.entity.base;

public enum LocationCategory {
    ACCOMMODATE("숙소"),
    TOUR("관광"),
    CAFE("카페"),
    RESTAURANT("식당");

    final private String name;
    public String getName(){
        return name;
    }
    LocationCategory(String name) {
        this.name = name;
    }
}
