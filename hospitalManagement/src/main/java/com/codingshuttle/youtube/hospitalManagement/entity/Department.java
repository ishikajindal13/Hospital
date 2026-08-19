package com.codingshuttle.youtube.hospitalManagement.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;


    // multiple mapping betweebn same entities
    @OneToOne     // 1 mapiing
    private Doctor headDoctor;

    @ManyToMany
    @JoinTable(
            name="my_dpt_doctors",
            joinColumns = @JoinColumn(name="dpt_id"),
            inverseJoinColumns = @JoinColumn(name="doctor_id")
    )
    // here we hab eto make a join table(doctor_department) - they cant directly connect // this hibernate will make itself when we create many to many
    private Set<Doctor> doctors = new HashSet<>();  // unique  // initialisation it  so that when hibernate will fill these
}