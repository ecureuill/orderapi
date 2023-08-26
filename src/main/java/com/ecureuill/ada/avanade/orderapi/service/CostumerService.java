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
import com.ecureuill.ada.avanade.orderapi.exceptions.UnauthorizedException;
import com.ecureuill.ada.avanade.orderapi.repository.CostumerRepository;
import com.ecureuill.ada.avanade.orderapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CostumerService {
    
    private final CostumerRepository repository;
    private final UserRepository userRepository;
    private final TokenService tokenService;

    public CostumerRecordDetail create(CostumerRecordCreate record, String token) throws UnauthorizedException {
        checkResourceOwner(record.username(), token);

        var user = userRepository.findByUsername(record.username());
        CostumerEntity costumer = record.toEntity();
        costumer.setUser(user.get());
        costumer = repository.save(costumer);
        return new CostumerRecordDetail(costumer);
    }

    public List<CostumerRecordDetail> findAll(String token) throws UnauthorizedException{

        checkAdmin(token);

        return repository.findAll().stream().map(CostumerRecordDetail::new).collect(Collectors.toList());
    }

    public CostumerRecordDetail findById(Long id, String token) throws NotFoundException, UnauthorizedException {
        Optional<CostumerEntity> user = repository.findById(id);
        
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("findById(%s)", id));
        }
        
        System.out.println(user.get());

        checkResourceOwner(user.get().getUser().getUsername(), token);

        return new CostumerRecordDetail(user.get());
    }

    public CostumerRecordDetail update(Long id, CostumerRecordUpdate record, String token) throws NotFoundException, UnauthorizedException {
        Optional<CostumerEntity> user = repository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException(String.format("findById(%s)", id));
        }
        
        checkResourceOwner(user.get().getUser().getUsername(), token);

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

    private void checkResourceOwner(String username, String token) throws UnauthorizedException {
        var jwt = token.replace("Bearer ", "");
        var authenticatedUser = tokenService.getSubject(jwt);

        if(!authenticatedUser.equals(username))
        {
            throw new UnauthorizedException();
        }
    }

    private void checkAdmin(String token) throws UnauthorizedException {
        var jwt = token.replace("Bearer ", "");
        var authenticatedUser = tokenService.getSubject(jwt);

        if(!authenticatedUser.equals("admin"))
        {
            throw new UnauthorizedException();
        }
    }


}
