package com.barmenu.security.auth;

import com.barmenu.security.exception.category.CategoryNameExistsException;
import com.barmenu.security.exception.user.UserEmailExistsException;
import com.barmenu.security.exception.user.UserNotFoundException;
import com.barmenu.security.exception.user.WrongPasswordException;
import com.barmenu.security.repo.UserRepository;
import com.barmenu.security.service.AuthenticationService;
import com.barmenu.security.user.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final UserRepository userRepo;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @Valid @RequestBody RegisterRequest request
    ) throws UserEmailExistsException {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @Valid @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PutMapping("/update-password")
    public ResponseEntity<UserResponseDTO> updatePassword(@Valid @RequestBody UserPasswordDTO dto, Authentication auth) throws UserNotFoundException, WrongPasswordException {
        var userDetails = (User) auth.getPrincipal();
        var user = service.updatePassword(userDetails.getId(), dto);
        return new ResponseEntity<>(new UserResponseDTO(user), HttpStatus.OK);
    }
}
