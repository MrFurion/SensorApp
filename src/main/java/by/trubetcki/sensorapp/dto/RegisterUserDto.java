package by.trubetcki.sensorapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class RegisterUserDto {
    @NotEmpty(message = "Name not should by empty")
    private String username;
    @NotEmpty(message = "Password not should by empty")
    private String password;
    @NotEmpty(message = "Email not should by empty")
    @Email
    private String email;
}
