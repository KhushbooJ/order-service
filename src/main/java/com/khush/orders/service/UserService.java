package com.khush.orders.service;

import com.khush.orders.dto.UserDto;
import com.khush.orders.model.User;
import com.khush.orders.repo.UserRepository;
import com.khush.orders.requests.RegisterUserRequest;
import com.khush.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    public UserDto findUserByName(String username) {
        UserDto userDto;
        //check cache first
        userDto = redisService.get("username", UserDto.class);
        if(userDto != null) {
            return userDto;
        } else {
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found with given name"));
            userDto = UserDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .build();
            //set user details in cache
            redisService.set("username", userDto, 36000L);
            return userDto;
        }
    }

    public Long registerUser(RegisterUserRequest request) {
        try {
            User user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .hashedPassword(passwordEncoder.encode(request.getRawPassword()))
                    .build();
        return userRepository.save(user).getId();
        } catch (Exception e) {
            log.error("Unable to register new user :"+e.getMessage());
            throw e;
        }
    }

}
