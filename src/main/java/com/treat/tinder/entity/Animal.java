package com.treat.tinder.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Animal {
    private long id;
    private String organization_id;
    private String url;
    private Breed breeds;
    private String age;
    private String gender;
    private String size;
    private String name;
    private Contact contact;
    private Links links;
}
