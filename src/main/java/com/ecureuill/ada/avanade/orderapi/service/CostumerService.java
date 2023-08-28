package com.ecureuill.ada.avanade.orderapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.ecureuill.ada.avanade.orderapi.dto.CostumerRecordCreate;
import com.ecureuill.ada.avanade.orderapi.dto.CostumerRecordDetail;
import com.ecureuill.ada.avanade.orderapi.dto.CostumerRecordUpdate;
import com.ecureuill.ada.avanade.orderapi.entity.CostumerEntity;
import com.ecureuill.ada.avanade.orderapi.exceptions.NotFoundException;
import com.ecureuill.ada.avanade.orderapi.exceptions.UnauthorizedException;
import com.ecureuill.ada.avanade.orderapi.repository.CostumerRepository;
import com.ecureuill.ada.avanade.orderapi.repository.UserRepository;
import com.ecureuill.ada.avanade.orderapi.utils.AuthenticatedUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CostumerService {
    
    private final CostumerRepository repository;
    private final UserRepository userRepository;

    public CostumerRecordDetail create(CostumerRecordCreate record) {
        var user = userRepository.findByUsername(AuthenticatedUser.getUsername());
        CostumerEntity costumer = record.toEntity();
        costumer.setUser(user.get());
        costumer = repository.save(costumer);
        return new CostumerRecordDetail(costumer);
    }

    public List<CostumerRecordDetail> findAll(){
        return repository.findAll().stream().map(CostumerRecordDetail::new).collect(Collectors.toList());
    }

    public CostumerRecordDetail findById(Long id) throws UnauthorizedException, NotFoundException {
        Optional<CostumerEntity> user = repository.findById(id);
        
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("findById(%s)", id));
        }
        
        AuthorizationService.isAuthenticatedUserOwnerOfResource(user.get().getUser().getUsername());
        
        return new CostumerRecordDetail(user.get());
    }

    public CostumerRecordDetail update(Long id, CostumerRecordUpdate record) throws NotFoundException, UnauthorizedException {
        Optional<CostumerEntity> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("findById(%s)", id));
        }
        
        AuthorizationService.isAuthenticatedUserOwnerOfResource(user.get().getUser().getUsername());

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
