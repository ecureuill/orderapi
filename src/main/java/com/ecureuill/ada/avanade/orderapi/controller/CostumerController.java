package com.ecureuill.ada.avanade.orderapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ecureuill.ada.avanade.orderapi.dto.CostumerRecordCreate;
import com.ecureuill.ada.avanade.orderapi.dto.CostumerRecordDetail;
import com.ecureuill.ada.avanade.orderapi.dto.CostumerRecordUpdate;
import com.ecureuill.ada.avanade.orderapi.exceptions.NotFoundException;
import com.ecureuill.ada.avanade.orderapi.exceptions.UnauthorizedException;
import com.ecureuill.ada.avanade.orderapi.service.CostumerService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/costumers")
@SecurityRequirement(name = "bearer-key")
public class CostumerController {

    @Autowired
    private CostumerService service;

    @PostMapping
    public ResponseEntity<CostumerRecordDetail> create(@RequestBody @Valid CostumerRecordCreate record, UriComponentsBuilder uriBuilder) {
        try {
            var user = service.create(record);
            var uri = uriBuilder.path("/costumers/{id}").buildAndExpand(user.id()).toUri();
          
            return ResponseEntity.created(uri).body(user);
            
        } catch (Exception e) {
            System.err.println(e.getMessage());
            // TODO: handle exception
        }
        return null;
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<CostumerRecordDetail>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CostumerRecordDetail> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CostumerRecordDetail> update(@PathVariable Long id, @RequestBody @Valid CostumerRecordUpdate record) {
        try {
            return ResponseEntity.ok(service.update(id, record));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
