package com.kazyon.orderapi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String name;
    private String email;
    private String role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private Store store;

    List<String> operations = null;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonManagedReference
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonManagedReference
    private List<Article> articles = new ArrayList<>();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonManagedReference
    private List<Supplier> suppliers = new ArrayList<>();

    public User(String username, String password, String name, String email, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;

    }
}
