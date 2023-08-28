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
import com.ecureuill.ada.avanade.orderapi.exceptions.ForbiddenException;
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
    public ResponseEntity<Void> register(@RequestBody @Valid UserRecord record, UriComponentsBuilder uriBuilder) throws UniqueKeyException {
        service.create(record);
        
        var uri = uriBuilder.path("/users/{username}").buildAndExpand(record.username()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserRecord>> getAllUsers() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserRecord> getUser(@PathVariable String username) throws ForbiddenException, NotFoundException, UnauthorizedException {
        var user = service.findByUsername(username);
        return ResponseEntity.ok(user);
    }  

    @PutMapping("/{username}")
    public ResponseEntity<Void> updateUser(@PathVariable String username, @RequestBody @Valid UserRecord record) throws NotFoundException, Exception {
        service.update(username, record);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteUser(@PathVariable String username) throws NotFoundException{
        service.delete(username);
        return ResponseEntity.noContent().build();
    }
    
}
