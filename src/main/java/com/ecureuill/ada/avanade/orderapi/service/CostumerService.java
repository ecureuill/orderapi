package com.ecureuill.ada.avanade.orderapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.ecureuill.ada.avanade.orderapi.dto.CostumerRecordCreate;
import com.ecureuill.ada.avanade.orderapi.dto.CostumerRecordDetail;
import com.ecureuill.ada.avanade.orderapi.dto.CostumerRecordUpdate;
import com.ecureuill.ada.avanade.orderapi.entity.CostumerEntity;
import com.ecureuill.ada.avanade.orderapi.exceptions.NotFoundException;
import com.ecureuill.ada.avanade.orderapi.repository.CostumerRepository;

@Service
public class CostumerService {
    
    @Autowired
    private CostumerRepository repository;
    
    public CostumerRecordDetail create(CostumerRecordCreate record) {
        CostumerEntity user = repository.save(record.toEntity());
        return new CostumerRecordDetail(user);
    }

    public List<CostumerRecordDetail> findAll(){
        return repository.findAll().stream().map(CostumerRecordDetail::new).collect(Collectors.toList());
    }

    public CostumerRecordDetail findById(Long id) throws NotFoundException {
        Optional<CostumerEntity> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("findById(%s)", id));
        }
        
        return new CostumerRecordDetail(user.get());
    }

    public CostumerRecordDetail update(Long id, CostumerRecordUpdate record) throws NotFoundException {
        Optional<CostumerEntity> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("findById(%s)", id));
        }
        
        CostumerEntity updatedUser = record.toEntity(user.get().getId());
        repository.save(updatedUser);
        return new CostumerRecordDetail(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) throws NotFoundException {
        Optional<CostumerEntity> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("findById(%s)", id));
        }
        repository.delete(user.get());
    }

}
