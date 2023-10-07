package com.barmenu.security.service;

import com.barmenu.security.exception.product.ProductNotFoundException;
import com.barmenu.security.exception.url.UrlExistsException;
import com.barmenu.security.exception.user.UserNotFoundException;
import com.barmenu.security.repo.UserRepository;
import com.barmenu.security.user.UrlDTO;
import com.barmenu.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    public String getUserUrl(Integer userId) {
        return userRepo.findUrlByUserId(userId);
    }

    public UrlDTO setUserUrl(Integer userId, UrlDTO dto) throws UrlExistsException, UserNotFoundException {
        if(userRepo.existsUserUrlWithDifferentId(userId, dto.getUrl()) > 0) {
            throw new UrlExistsException();
        }
        var user = userRepo.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        user.setUrl(dto.getUrl());
        userRepo.save(user);
        return dto;
    }
}
