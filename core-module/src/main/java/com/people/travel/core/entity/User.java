package com.people.travel.core.entity;

import com.people.travel.core.entity.base.Address;
import com.people.travel.core.entity.base.TimeStamped;
import com.people.travel.core.entity.enums.Mbti;
import com.people.travel.core.entity.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity(name = "Member")
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nickName;

    @Column(unique = true, nullable = false)
    private String LoginId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String realName;

    private String phoneNum;

    @Column(nullable = false)
    private String email;

    @Embedded
    Address address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Mbti mbti;

    @Enumerated(EnumType.STRING)
    Role role = Role.USER;
}
