package com.khush.orders.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserRequest {

    @NotBlank
    @Valid
    private String username;

    @NotBlank
    @Email
    @Valid
    private String email;

    @NotBlank
    @Size(min = 8)
    @Valid
    private String rawPassword;

    @NotBlank
    @Valid
    private String phoneNumber;
}
