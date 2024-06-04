package com.ballesteros.api.security.auth.modeldto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {

    @Size(min = 4, message = "Username must be at least 4 characters")
    String username;
    @Email(message = "Email should be valid")
    @Size(min = 4, message = "Email must be at least 4 characters")
    String email;
    @Size(min = 4, message = "Password must be at least 4 characters")
    String password;

}
