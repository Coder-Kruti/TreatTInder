package com.treat.tinder.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerRequest {
    @NotNull
    private String name;
    @NotNull
    private String phoneNumber;
}
