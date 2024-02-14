package com.example.treat.tinder.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pagination {

    private int count_per_page;
    private int total_count;
    private int current_page;
    private int total_pages;
    @JsonIgnore
    private Object _links;

}