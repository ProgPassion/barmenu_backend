package com.barmenu.security.service;

import com.barmenu.security.auth.AuthenticationRequest;
import com.barmenu.security.auth.AuthenticationResponse;
import com.barmenu.security.auth.RegisterRequest;
import com.barmenu.security.auth.UserPasswordDTO;
import com.barmenu.security.config.JwtService;
import com.barmenu.security.exception.category.CategoryNameExistsException;
import com.barmenu.security.exception.user.UserEmailExistsException;
import com.barmenu.security.exception.user.UserNotFoundException;
import com.barmenu.security.exception.user.WrongPasswordException;
import com.barmenu.security.repo.UserRepository;
import com.barmenu.security.user.Role;
import com.barmenu.security.user.User;
import com.barmenu.security.utils.RandomStrGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final CategoryService categoryService;
    public AuthenticationResponse register(RegisterRequest request) throws UserEmailExistsException{
        if(repository.findByEmail(request.getEmail()).isPresent()) {
            throw new UserEmailExistsException();
        }
        var user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .url(RandomStrGenerator.generate())
                .build();
        var userSaved = repository.save(user);
        categoryService.addDefaultCategory(userSaved);
        return jwtService.generateToken(user);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow();
        return jwtService.generateToken(user);

    }

    public User updatePassword(Integer userId, UserPasswordDTO dto) throws UserNotFoundException, WrongPasswordException {
        final var user = repository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if(!passwordEncoder.matches(dto.getCurrentPassword(), user.getPassword())) {
            throw new WrongPasswordException();
        }
        user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
        return repository.save(user);
    }

}
