package by.trubetcki.sensorapp.controller;

import by.trubetcki.sensorapp.dto.LoginUserDto;
import by.trubetcki.sensorapp.dto.RegisterUserDto;
import by.trubetcki.sensorapp.models.User;
import by.trubetcki.sensorapp.services.AuthenticationService;
import by.trubetcki.sensorapp.services.JwtService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
@Slf4j
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @Operation(summary = "Register new user")
    @PostMapping("/signup")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterUserDto registerUserDto,
                                      BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            for (ObjectError error : bindingResult.getAllErrors()) {
                log.error("Validation errors occurred while processing user registration: - " + error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @Operation(summary = "Authenticate user by name and password")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}
