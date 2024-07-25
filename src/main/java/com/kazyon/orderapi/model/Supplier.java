package com.kazyon.orderapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private int code;

    private String name;
    private String address;

    private Integer fiscalIds;


    private ZonedDateTime createdAt;
    @PrePersist
    public void onPrePersist() {
        createdAt = ZonedDateTime.now();
    }

    @ToString.Exclude
    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
//    @JsonIgnore
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "supplier", fetch = FetchType.LAZY)
//    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
