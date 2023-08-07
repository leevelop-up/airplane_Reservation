package com.github.supercoding.repository.users;

import lombok.*;

import java.util.Objects;
@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "userId")
@Builder
public class UserEntity {
    private Integer userId;
    private String userName;
    private String likeTravelPlace;
    private  String phoneNum;

}
