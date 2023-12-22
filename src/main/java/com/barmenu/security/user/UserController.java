package com.barmenu.security.user;

import com.barmenu.security.exception.url.UrlExistsException;
import com.barmenu.security.exception.user.UserNotFoundException;
import com.barmenu.security.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/geturl")
    public ResponseEntity<String> getUrl(Authentication auth) {
        var userDetails = (User) auth.getPrincipal();
        String url = userService.getUserUrl(userDetails.getId());
        return ResponseEntity.ok(url);
    }

    @PutMapping("/seturl")
    public ResponseEntity<UrlDTO> setUrl(@Valid @RequestBody UrlDTO dto, Authentication auth) throws UrlExistsException, UserNotFoundException {
        var userDetails = (User) auth.getPrincipal();
        UrlDTO responseDto = userService.setUserUrl(userDetails.getId(), dto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/getbusinessname")
    public ResponseEntity<String> getBusinessName(Authentication auth) {
        var userDetails = (User) auth.getPrincipal();
        String businessName = userService.getBusinessName(userDetails.getId());
        return ResponseEntity.ok(businessName);
    }

    @PutMapping("/setbusinessname")
    public ResponseEntity<BusinessNameDTO> setBusinessName(@Valid @RequestBody BusinessNameDTO dto, Authentication auth) throws UserNotFoundException {
        var userDetails = (User) auth.getPrincipal();
        BusinessNameDTO responseDto = userService.setBusinessName(userDetails.getId(), dto);
        return ResponseEntity.ok(responseDto);
    }
}
