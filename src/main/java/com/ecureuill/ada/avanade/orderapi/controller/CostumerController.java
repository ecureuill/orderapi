package com.ecureuill.ada.avanade.orderapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.ecureuill.ada.avanade.orderapi.dto.CostumerRecordCreate;
import com.ecureuill.ada.avanade.orderapi.dto.CostumerRecordDetail;
import com.ecureuill.ada.avanade.orderapi.dto.CostumerRecordUpdate;
import com.ecureuill.ada.avanade.orderapi.service.CostumerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/costumers")
public class CostumerController {

    @Autowired
    private CostumerService service;

    @PostMapping
    public ResponseEntity<CostumerRecordDetail> create(@RequestBody @Valid CostumerRecordCreate record, UriComponentsBuilder uriBuilder) {
        var user = service.create(record);
        var uri = uriBuilder.path("/users/{id}").buildAndExpand(user.id()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @GetMapping
    public ResponseEntity<List<CostumerRecordDetail>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CostumerRecordDetail> findById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CostumerRecordDetail> update(@PathVariable Long id, @RequestBody @Valid CostumerRecordUpdate record) {
        try {
            return ResponseEntity.ok(service.update(id, record));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
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
