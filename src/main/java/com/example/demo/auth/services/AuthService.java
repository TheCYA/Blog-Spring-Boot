package com.example.demo.auth.services;

import com.example.demo.user.Role;
import com.example.demo.user.User;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.demo.auth.dto.LoginResponse;
import com.example.demo.auth.TokenBlackList;
import com.example.demo.auth.dto.LoginRequest;
import com.example.demo.auth.dto.RegisterRequest;
import com.example.demo.user.UserRepository;
import com.example.demo.user.UserService;

@Service
public class AuthService {


    private final TokenBlackList tokenBlackList;
    private final UserRepository userRepository;
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(
        UserRepository userRepository, 
        UserService userService, 
        JwtService jwtService, 
        TokenBlackList tokenBlackList,
        AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.jwtService = jwtService;
        this.tokenBlackList = tokenBlackList;
        this.authenticationManager = authenticationManager;
    }

    public String register(RegisterRequest request){
        if(userRepository.existsByEmail(request.getEmail())){ throw new IllegalArgumentException("Este email ya está en uso"); }
        if(userRepository.existsByUsername(request.getUsername())){ throw new IllegalArgumentException("Este nombre de usuario ya está en uso"); }
        if(request.getPassword().length() < 8){ throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres"); }
        System.out.println("Register");

        User user = new User();
        user.setEmail(request.getEmail().toLowerCase());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRole(request.getRole());

        userService.postUser(user);

        return "Usuario registrado correctamente.";
    }

    public LoginResponse login( LoginRequest request ){
        System.out.println(request);
        if(request == null){ throw new IllegalArgumentException("Faltan datos de inicio de sesión"); }
        if(request.getEmailOrUsername() == null || request.getPassword() == null){
            System.out.println("faltan datos");
            throw new IllegalArgumentException("Faltan datos de inicio de sesión");
        }
        
        String username = request.getEmailOrUsername().contains("@") 
        ? userService.getUserByEmail(request.getEmailOrUsername().toLowerCase()).getUsername() 
        : request.getEmailOrUsername();

        Authentication auth = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                username,
                request.getPassword()
            )
        );
        if (auth == null || !auth.isAuthenticated()) {
            System.out.println("Credenciales inválidas");
            throw new IllegalArgumentException("Credenciales inválidas");
        }
        System.out.println("Usuario autenticado" + auth.getPrincipal());

        User userAuthenticated = (User) auth.getPrincipal();
        String token = jwtService.getToken(userAuthenticated);
        return new LoginResponse(
            token,
            jwtService.getId(token),
            jwtService.getUsername(token),
            jwtService.getEmail(token),
            Role.valueOf(jwtService.getRole(token)),
            "Inicio de sesión exitoso."
        );
    }

    public String logout(){
        String token = jwtService.getTokenFromContext();
        if(token == null || token.isEmpty()){
            throw new IllegalArgumentException("Token de sesión no proporcionado");
        }
        if(!tokenBlackList.containsToken(token)){
            tokenBlackList.addToken(token);
        } else {
            throw new IllegalArgumentException("Token ya ha sido invalidado");
        }

        return "Cerrado de sesión exitoso";
    }

}
