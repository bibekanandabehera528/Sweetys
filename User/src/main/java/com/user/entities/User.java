package com.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "users")
public class User {
    @Id
    private String userId;
    @Column(length = 100)
    private String name;
    @Column(length = 100)
    private String email;
    @Column(length = 20)
    private String password;
    @Column(length = 20)
    private String gender;
    private String userImage;
    @Column(length = 500)
    private String about;
}
