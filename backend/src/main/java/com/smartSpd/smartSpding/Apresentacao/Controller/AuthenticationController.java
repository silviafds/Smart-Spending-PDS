package com.smartSpd.smartSpding.Apresentacao.Controller;

import com.smartSpd.smartSpding.Core.DTO.AuthenticationDTO;
import com.smartSpd.smartSpding.Core.DTO.LoginResponseDTO;
import com.smartSpd.smartSpding.Core.DTO.UserDTO;
import com.smartSpd.smartSpding.Core.Dominio.User;
import com.smartSpd.smartSpding.Core.Dominio.Role;
import com.smartSpd.smartSpding.Infraestructure.Repositorio.UserRepository;
import com.smartSpd.smartSpding.Infraestructure.Seguranca.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    public AuthenticationController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            var token = tokenService.generateToken((User) auth.getPrincipal());
            return ResponseEntity.ok(new LoginResponseDTO(token));
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login e/ou senha inválido.");
        }
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserDTO data) {
        Role roleConvertido = Role.fromString(data.getRole());
        if(this.userRepository.findByLogin(data.getLogin()) != null) {
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"message\":  \"Login ja existe no sistema.\"}");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.getPassword());

        User newUser = new User(data.getNome(), data.getSobrenome(), data.getLogin(), data.getDatanascimento(),
                data.getEmail(), encryptedPassword, roleConvertido);

        this.userRepository.save(newUser);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"Registro realizado com sucesso\"}");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("logout: "+request);
        String token = extractTokenFromHeader(request);
        tokenService.addToBlacklist(token);

        return ResponseEntity.ok("Logout successful");
    }

    private String extractTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }
}
