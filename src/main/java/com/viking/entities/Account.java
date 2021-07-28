package com.viking.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
  @UniqueConstraint(columnNames = "email") })
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String username;

    private String fullName;

    private String email;

    private String password;

    private String photo;

    @JsonIgnore
    @OneToMany(mappedBy = "account")
    List<Order> orders;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id")
                , inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();


    public Account(String username, String email, String password) {

        this.username = username;
        this.email = email;
        this.password = password;

    }

    public Account() {
    }
}
