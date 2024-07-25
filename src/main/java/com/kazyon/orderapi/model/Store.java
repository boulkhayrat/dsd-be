package com.kazyon.orderapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "store")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;
    private String address;
    private String city;
    private String busArea;


    private ZonedDateTime createdAt;
    @PrePersist
    public void onPrePersist() {
        createdAt = ZonedDateTime.now();
    }

    @OneToOne(mappedBy = "store")
    private User user;


}
