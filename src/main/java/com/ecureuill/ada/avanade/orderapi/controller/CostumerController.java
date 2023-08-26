package com.ecureuill.ada.avanade.orderapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@RestController
@RequestMapping("/costumers")
@SecurityRequirement(name = "bearer-key")
public class CostumerController {

    @Autowired
    private CostumerService service;

    @PostMapping
    public ResponseEntity<CostumerRecordDetail> create(@RequestBody @Valid CostumerRecordCreate record, @RequestHeader (name="Authorization") String token, UriComponentsBuilder uriBuilder) {
        try {
            var user = service.create(record, token);
            var uri = uriBuilder.path("/costumers/{id}").buildAndExpand(user.id()).toUri();
        
            return ResponseEntity.created(uri).body(user);
        
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(403).build();
        
        }
    }

    @GetMapping
    public ResponseEntity<List<CostumerRecordDetail>> findAll(@RequestHeader (name="Authorization") String token){
        try {
            return ResponseEntity.ok(service.findAll(token));
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CostumerRecordDetail> findById(@PathVariable Long id, @RequestHeader (name="Authorization") String token) {
        try {
            return ResponseEntity.ok(service.findById(id, token));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CostumerRecordDetail> update(@PathVariable Long id, @RequestBody @Valid CostumerRecordUpdate record, @RequestHeader (name="Authorization") String token) {
        try {
            return ResponseEntity.ok(service.update(id, record, token));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UnauthorizedException e) {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
