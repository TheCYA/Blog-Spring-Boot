package com.example.demo.user;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.user.dto.UserPatchRequest;
import com.example.demo.user.dto.UserUpdateRequest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){ 
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers(){ return userRepository.findAll(); }

    public User getUserById(Long id){ return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con Id: " + id));}

    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con email: " + email));
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con nombre de usuario: " + username));
    }

    @Transactional
    public User postUser(User user){
        if(user == null){ throw new IllegalArgumentException("Faltan datos"); }
        if(userRepository.existsByEmail(user.getEmail())){ throw new IllegalArgumentException("Este email ya está en uso"); }
        if(userRepository.existsByUsername(user.getUsername())){ throw new IllegalArgumentException("Este nombre de usuario ya está en uso"); }
        if(user.getPassword().length() < 8){ throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres"); }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    @Transactional
    public User patchUser(UserPatchRequest userDetails){
        if (userDetails == null) {
            throw new IllegalArgumentException("Faltan datos");
        }

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = currentUser.getId();
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (userDetails.getEmail() != null && !userDetails.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userDetails.getEmail())) {
                throw new IllegalStateException("El email ya está en uso");
            }
            user.setEmail(userDetails.getEmail().toLowerCase());
        }

        if (userDetails.getUsername() != null && !userDetails.getUsername().equals(user.getUsername())) {
            if (userRepository.existsByUsername(userDetails.getUsername())) {
                throw new IllegalStateException("Ese nombre de usuario ya está en uso");
            }
            user.setUsername((userDetails.getUsername()));
        }

        if (userDetails.getPassword() != null && !userDetails.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        if (userDetails.getRole() != null && !userDetails.getRole().equals(user.getRole())) {
            user.setRole(userDetails.getRole());
        }

        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(UserUpdateRequest userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("Faltan datos");
        }

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long id = currentUser.getId();

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        if (!userDetails.getEmail().equals(user.getEmail()) &&
            userRepository.existsByEmail(userDetails.getEmail())) {
            throw new IllegalStateException("Este email ya está en uso");
        }

        if (!userDetails.getUsername().equals(user.getUsername()) &&
            userRepository.existsByUsername(userDetails.getUsername())) {
            throw new IllegalStateException("Este nombre de usuario ya está en uso");
        }

        user.setEmail(userDetails.getEmail().toLowerCase());
        user.setUsername(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        user.setRole(userDetails.getRole());

        return userRepository.save(user);
    }


    @Transactional
    public void deleteUser(Long id){
        User user = getUserById(id);
        userRepository.delete(user);
    }
}
