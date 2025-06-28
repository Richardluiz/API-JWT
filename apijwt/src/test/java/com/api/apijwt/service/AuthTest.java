package com.api.apijwt.service;

import com.api.apijwt.model.User;
import com.api.apijwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("admin");
        user.setPassword("123456");
        user.setRole("USER");
    }

    @Test
    void testAutenticarUsuarioComSucesso() {
        // Arrange
        when(userRepository.findByUsername("teste")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", user.getPassword())).thenReturn(true);
        when(jwtService.generateToken("teste", "USER")).thenReturn("token-mock");

        // Act
        String token = authService.login("teste", "123456");

        // Assert
        assertEquals("token-mock", token);
        verify(userRepository).findByUsername("teste");
        verify(jwtService).generateToken("teste", "USER");
    }
}
