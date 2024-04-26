package com.user.services.impl;

import com.user.dtos.UserDto;
import com.user.entities.User;
import com.user.exceptions.ResourceNotFoundException;
import com.user.repositories.UserRepository;
import com.user.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Value("${user.image.path}")
    private String userImagePath;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setUserId(UUID.randomUUID().toString());
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " doesn't exist !!!"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers(int pageSize, int pageNumber, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        List<User> users = userRepository.findAll(pageable).getContent();
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " doesn't exist !!!"));
        File file = new File(userImagePath+user.getUserImage());
        file.delete();
        userRepository.delete(user);
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " doesn't exist !!!"));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public void uploadUserImage(MultipartFile userImage, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " doesn't exist !!!"));
        File file = new File(userImagePath);
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            String userImageName = UUID.randomUUID().toString() + userImage.getOriginalFilename();
            userImage.transferTo(new File(userImagePath + userImageName));
            user.setUserImage(userImageName);
            userRepository.save(user);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public InputStream viewUserImage(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " doesn't exist !!!"));
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(userImagePath + user.getUserImage());
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return inputStream;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " doesn't exist !!!"));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.searchUser(keyword);
        return users.stream().map(user -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }
}
