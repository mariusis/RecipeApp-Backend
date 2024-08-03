package com.RecipeSharing.RecipeSharing.auth;

import com.RecipeSharing.RecipeSharing.config.JwtService;
import com.RecipeSharing.RecipeSharing.user.Role;
import com.RecipeSharing.RecipeSharing.user.User;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/auth")
public class JwtExtractController {

    @Autowired
    private JwtService jwtService;

    @GetMapping("/extractUser")
    public ResponseEntity<User> extractUser(@RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");

        User user = new User();
        user.setEmail(jwtService.extractUsername(token));
        user.setRole(Role.USER);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/extractUserId")
    public ResponseEntity<Integer> extractUserId(@RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.replace("Bearer ", "");

        return ResponseEntity.ok(jwtService.getUserId(token));
    }




}