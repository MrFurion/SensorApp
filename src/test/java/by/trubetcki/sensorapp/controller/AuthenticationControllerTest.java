package by.trubetcki.sensorapp.controller;

import by.trubetcki.sensorapp.dto.LoginUserDto;
import by.trubetcki.sensorapp.dto.RegisterUserDto;
import by.trubetcki.sensorapp.models.User;
import by.trubetcki.sensorapp.services.AuthenticationService;
import by.trubetcki.sensorapp.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    private RegisterUserDto registerUserDto;
    private LoginUserDto loginUserDto;
    private User user;
    private LoginResponse loginResponse;

    @BeforeEach
    void setUp() {
        registerUserDto = new RegisterUserDto();
        registerUserDto.setUsername("testuser");
        registerUserDto.setPassword("password");
        registerUserDto.setEmail("testuser@example.com");

        loginUserDto = new LoginUserDto();
        loginUserDto.setUsername("testuser");
        loginUserDto.setPassword("password");

        user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setEmail("testuser@example.com");
        user.setRole("ROLE_ADMIN");

        loginResponse = new LoginResponse();
        loginResponse.setToken("test-token");
        loginResponse.setExpiresIn(3600L);
    }

    @Test
    void testRegister_Success() throws Exception {
        when(authenticationService.signup(any(RegisterUserDto.class))).thenReturn(user);

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"));
    }

    @Test
    void testRegister_ValidationErrors() throws Exception {
        RegisterUserDto invalidUserDto = new RegisterUserDto(); // Missing required fields

        mockMvc.perform(post("/auth/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidUserDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].defaultMessage").exists());
    }

    @Test
    void testAuthenticate_Success() throws Exception {
        when(authenticationService.authenticate(any(LoginUserDto.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("test-token");
        when(jwtService.getExpirationTime()).thenReturn(3600L);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginUserDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("test-token"))
                .andExpect(jsonPath("$.expiresIn").value(3600L));
    }
}
