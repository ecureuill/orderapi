package com.ecureuill.ada.avanade.orderapi.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.ecureuill.ada.avanade.orderapi.dto.CostumerRecordCreate;
import com.ecureuill.ada.avanade.orderapi.dto.CustomerRecordDetail;
import com.ecureuill.ada.avanade.orderapi.dto.CostumerRecordUpdate;
import com.ecureuill.ada.avanade.orderapi.entity.CustomerEntity;
import com.ecureuill.ada.avanade.orderapi.exceptions.NotFoundException;
import com.ecureuill.ada.avanade.orderapi.exceptions.UnauthorizedException;
import com.ecureuill.ada.avanade.orderapi.repository.CustomerRepository;
import com.ecureuill.ada.avanade.orderapi.repository.UserRepository;
import com.ecureuill.ada.avanade.orderapi.utils.AuthenticatedUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
    
    private final CustomerRepository repository;
    private final UserRepository userRepository;

    public CustomerRecordDetail create(CostumerRecordCreate record) {
        var user = userRepository.findByUsername(AuthenticatedUser.getUsername());
        CustomerEntity costumer = record.toEntity();
        costumer.setUser(user.get());
        costumer = repository.save(costumer);
        return new CustomerRecordDetail(costumer);
    }

    public List<CustomerRecordDetail> findAll(){
        return repository.findAll().stream().map(CustomerRecordDetail::new).collect(Collectors.toList());
    }

    public CustomerRecordDetail findById(Long id) throws UnauthorizedException, NotFoundException {
        Optional<CustomerEntity> user = repository.findById(id);
        
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("findById(%s)", id));
        }
        
        AuthorizationService.isAuthenticatedUserOwnerOfResource(user.get().getUser().getUsername());
        
        return new CustomerRecordDetail(user.get());
    }

    public CustomerRecordDetail update(Long id, CostumerRecordUpdate record) throws NotFoundException, UnauthorizedException {
        Optional<CustomerEntity> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("findById(%s)", id));
        }
        
        AuthorizationService.isAuthenticatedUserOwnerOfResource(user.get().getUser().getUsername());

        CustomerEntity updatedUser = record.toEntity(user.get().getId());
        repository.save(updatedUser);
        return new CustomerRecordDetail(updatedUser);
    }

    @DeleteMapping("/{id}")
    public void delete(Long id) throws NotFoundException {
        Optional<CustomerEntity> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("findById(%s)", id));
        }

        repository.delete(user.get());
    }
}
