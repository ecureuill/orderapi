package com.ecureuill.ada.avanade.orderapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ecureuill.ada.avanade.orderapi.dto.UserRecord;
import com.ecureuill.ada.avanade.orderapi.exceptions.NotFoundException;
import com.ecureuill.ada.avanade.orderapi.exceptions.UnauthorizedException;
import com.ecureuill.ada.avanade.orderapi.exceptions.UniqueKeyException;
import com.ecureuill.ada.avanade.orderapi.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class UserController {

    private final UserService service;

    @PostMapping
    public ResponseEntity<String> register(@RequestBody @Valid UserRecord record, UriComponentsBuilder uriBuilder) {
        try {
            service.create(record);
        } catch (UniqueKeyException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        var uri = uriBuilder.path("/users/{username}").buildAndExpand(record.username()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserRecord>> getAllUsers() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserRecord> getUser(@PathVariable String username) throws UnauthorizedException {
        try {
            return ResponseEntity.ok(service.findByUsername(username));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }  

    @PutMapping("/{username}")
    public ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody @Valid UserRecord record) throws Exception {
        try {
            service.update(username, record);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String username){
        try {
            service.delete(username);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
}
