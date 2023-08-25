package com.ecureuill.ada.avanade.orderapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecureuill.ada.avanade.orderapi.dto.TokenRecord;
import com.ecureuill.ada.avanade.orderapi.dto.UserRecord;
import com.ecureuill.ada.avanade.orderapi.entity.UserEntity;
import com.ecureuill.ada.avanade.orderapi.service.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private AuthenticationManager manager;
    private TokenService tokenService;

    public AuthController(AuthenticationManager manager, TokenService tokenService) {
        this.manager = manager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenRecord> authenticate(@RequestBody @Valid UserRecord data) {
        var token = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var authenticate = manager.authenticate(token);
        var tokenJwt = tokenService.generateToken((UserEntity)authenticate.getPrincipal());
        
        return ResponseEntity.ok(new TokenRecord(tokenJwt));
    }
}
