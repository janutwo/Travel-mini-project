package com.people.travel.core.entity.base;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Address {

    private String address;

    private String detailAddress;

    private String extraAddress;

    private String postcode;

    private String mapLink;

}
