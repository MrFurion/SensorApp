package by.trubetcki.sensorapp.services.impl;

import by.trubetcki.sensorapp.dto.LoginUserDto;
import by.trubetcki.sensorapp.dto.RegisterUserDto;
import by.trubetcki.sensorapp.dto.ResponseRegisterDto;
import by.trubetcki.sensorapp.enums.Roles;
import by.trubetcki.sensorapp.models.User;
import by.trubetcki.sensorapp.repositories.UserRepository;
import by.trubetcki.sensorapp.services.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public ResponseRegisterDto signup(RegisterUserDto input) {
        User user = new User();
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRole(Roles.VIEWER.getRoleName());
        userRepository.save(user);

        ResponseRegisterDto registerUserDto = new ResponseRegisterDto();
        registerUserDto.setUsername(input.getUsername());

        return registerUserDto;
    }

    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );
        return userRepository.findByUsername(input.getUsername())
                .orElseThrow();
    }
}
