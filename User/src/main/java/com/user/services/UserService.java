package com.user.services;

import com.user.dtos.UserDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

public interface UserService {
    public UserDto addUser(UserDto userDto);
    public UserDto getUserById(String userId);
    public List<UserDto> getAllUsers(int pageSize, int pageNumber, String sortBy);
    public void deleteUser(String userId);
    public UserDto updateUser(String userId, UserDto userDto);
    public void uploadUserImage(MultipartFile userImage, String userId);
    public InputStream viewUserImage(String userId);

    public UserDto getUserByEmail(String email);

    public List<UserDto> searchUser(String keyword);
}
