package com.treat.tinder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int breedId;

//    @OneToOne
//    @JoinColumn(name = "id")
    private int dogId;

    @Column(name = "primary_breed")
    private String primary;
    @Column(name = "secondary_breed")
    private String secondary;
    private boolean mixed;
    @Column(name = "not_known")
    private boolean unknown;

}