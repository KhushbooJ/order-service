package com.khush.orders.controller;

import com.khush.orders.dto.UserDto;
import com.khush.orders.requests.RegisterUserRequest;
import com.khush.orders.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> findUserByName(@PathVariable final String username) {
        UserDto user = userService.findUserByName(username);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<?> registerUser(@Valid @RequestBody final RegisterUserRequest request) {
        try {
            return ResponseEntity.ok(userService.registerUser(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder(e,HttpStatus.BAD_REQUEST,e.getMessage()));
        }
    }
}
