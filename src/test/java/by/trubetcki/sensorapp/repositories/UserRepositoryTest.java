package by.trubetcki.sensorapp.repositories;

import by.trubetcki.sensorapp.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.PropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
@PropertySource("classpath:test.properties")
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    private User user;
    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password");
        userRepository.save(user);
    }
    @Test
    void testFindByUsername() {
        Optional<User> foundUser = userRepository.findByUsername("testuser");
        assertTrue(foundUser.isPresent());
        assertEquals("testuser@example.com", foundUser.get().getEmail());
    }

    @Test
    void testNotFindByUsername(){
        Optional<User> foundUser = userRepository.findByUsername("notuser");
        assertFalse(foundUser.isPresent());
    }
}
