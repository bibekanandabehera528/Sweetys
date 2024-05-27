package com.order.dtos;

import lombok.Data;

@Data
public class User {
    private String userId;
    private String name;
    private String email;
    private String password;
    private String gender;
    private String userImage;
    private String about;
}
