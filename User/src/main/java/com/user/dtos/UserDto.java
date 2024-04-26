package com.user.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserDto {
    private String userId;
    @NotEmpty
    @Length(min = 3,max = 100)
    private String name;
    @Length(min = 10,max = 100)
    @NotEmpty
    private String email;
    @Length(min = 8,max = 30)
    @NotEmpty
    private String password;
    @Length(min = 4,max = 20)
    @NotEmpty
    private String gender;
    private String userImage;
    @Length(min = 10,max = 500)
    @NotNull
    private String about;
}
