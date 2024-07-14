package by.trubetcki.sensorapp.services;

import by.trubetcki.sensorapp.dto.LoginUserDto;
import by.trubetcki.sensorapp.dto.RegisterUserDto;
import by.trubetcki.sensorapp.dto.ResponseRegisterDto;
import by.trubetcki.sensorapp.models.User;

public interface AuthenticationService {
    /**
     * Registers a new user in the system.
     *
     * @param input the registration details of the user
     * @return the registered user
     */
    ResponseRegisterDto signup(RegisterUserDto input);

    /**
     * Authenticates a user based on their username and password.
     *
     * @param input the login details of the user
     * @return the authenticated user
     */
    User authenticate(LoginUserDto input);
}
