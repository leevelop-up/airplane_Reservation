package com.github.supercoding.repository.passenger;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode(of = "passengerId")
@Builder
public class Passenger {
    private Integer passengerId;
    private Integer userId;
    private String passportNum;
}
