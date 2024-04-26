package com.user.controller;

import com.user.dtos.UserDto;
import com.user.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/addUser/")
    public ResponseEntity<UserDto> addUser(@RequestBody @Valid UserDto userDto) {
        logger.info("Adding new user: " + userDto.getUserId());
        return new ResponseEntity<>(userService.addUser(userDto), HttpStatus.CREATED);
    }

    @GetMapping("/getUserById/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        logger.info("Fetching user data of: " + userId);
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                                     @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
                                                     @RequestParam(value = "sortBy", defaultValue = "userId", required = false) String sortBy) {
        logger.info("Fetch all users...");
        return new ResponseEntity<>(userService.getAllUsers(pageSize, pageNumber, sortBy), HttpStatus.OK);
    }

    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        logger.info("Removing user : " + userId);
        return new ResponseEntity<>("User with id " + userId + " has been removed !!!", HttpStatus.OK);
    }

    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable String userId, @RequestBody @Valid UserDto userDto) {
        logger.info("Updating user: " + userId);
        return new ResponseEntity<>(userService.updateUser(userId, userDto), HttpStatus.CREATED);
    }

    @PostMapping("/uploadUserImage/{userId}")
    public ResponseEntity<String> uploadUserImage(@PathVariable String userId, MultipartFile userImage) {

        userService.uploadUserImage(userImage, userId);
        logger.info("Uploading profile image for user: " + userId);
        return new ResponseEntity<>("User image has been uploaded for user : " + userId, HttpStatus.OK);
    }

    @GetMapping("/displayUserImage/{userId}")
    public ResponseEntity<?> displayUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(userService.viewUserImage(userId), response.getOutputStream());
        logger.info("Fetching profile image of user: " + userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/searchUser/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable("keyword") String keyword) {
        return new ResponseEntity<>(userService.searchUser(keyword), HttpStatus.OK);
    }

    @GetMapping("/searchUserByEmail/{email}")
    public ResponseEntity<UserDto> searchUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }
}
