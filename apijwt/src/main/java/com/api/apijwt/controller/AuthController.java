package com.api.apijwt.controller;

import com.api.apijwt.dto.LoginRequest;
import com.api.apijwt.model.User;
import com.api.apijwt.repository.UserRepository;
import com.api.apijwt.service.AuthService;
import com.api.apijwt.service.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    @Operation(summary = "Registra um novo usuário com username e password")
    public ResponseEntity<String> register(@RequestBody LoginRequest loginRequest) {
        if (userRepository.findByUsername(loginRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Usuário já existe!");
        }

        User newUser = new User(
            loginRequest.getUsername(),
            passwordEncoder.encode(loginRequest.getPassword()),
            "ROLE_USER" // papel padrão
        );

        userRepository.save(newUser);
        return ResponseEntity.ok("Usuário criado com sucesso!");
    }

    @PostMapping("/login")
    @Operation(summary = "Realiza login e retorna JWT")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.getUsername(), loginRequest.getPassword());
        if (token == null) {
            return ResponseEntity.status(401).body("Usuário ou senha inválidos");
        }
        return ResponseEntity.ok(token);
    }

    @PostMapping("/validate")
    @Operation(summary = "Valida um token JWT")
    public ResponseEntity<Boolean> validateToken(@RequestParam String token) {
        boolean valid = jwtService.validateToken(token);
        return ResponseEntity.ok(valid);
    }

    @GetMapping("/users")
    @Operation(summary = "Lista todos os usuários cadastrados")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
    
    @DeleteMapping("/users/{username}")
    @Operation(summary = "Deleta um usuário pelo username")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok("Usuário deletado com sucesso!");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}