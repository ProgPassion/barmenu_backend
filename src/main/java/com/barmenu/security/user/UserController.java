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
}
