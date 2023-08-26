package com.ecureuill.ada.avanade.orderapi.service;

import org.springframework.stereotype.Service;

import com.ecureuill.ada.avanade.orderapi.dto.UserRecord;
import com.ecureuill.ada.avanade.orderapi.entity.UserEntity;
import com.ecureuill.ada.avanade.orderapi.exceptions.NotFoundException;
import com.ecureuill.ada.avanade.orderapi.exceptions.UnauthorizedException;
import com.ecureuill.ada.avanade.orderapi.exceptions.UniqueKeyException;
import com.ecureuill.ada.avanade.orderapi.repository.UserRepository;
import com.ecureuill.ada.avanade.orderapi.utils.AuthenticatedUser;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    
    public void create(UserRecord user) throws UniqueKeyException{
        var result = userRepository.findByUsername(user.username());
        
        if(result.isPresent()) {
            throw new UniqueKeyException("user " + user.username() + " already registered");
        };
        
        userRepository.save(new UserEntity(null, user.username(), user.password()));
    }

    public void update(String username, UserRecord data) throws Exception{
        var user = userRepository.findByUsername(username);

        if(user.isEmpty())
            throw new NotFoundException("User not found");

        if(!data.username().equals(username))
            throw new Exception("Username can not be changed");

        if(AuthenticatedUser.getRole().equals("ADMIN") || AuthorizationService.isAuthenticatedUserOwnerOfResource(username)) {
            userRepository.save(new UserEntity(user.get().getId(), data.username(), data.password()));
            return;
        }
        
        throw new UnauthorizedException();
    }
    
    public void delete(String username) throws NotFoundException{
        var user = userRepository.findByUsername(username);

        if(user.isPresent())
            userRepository.delete(user.get());
        
        throw new NotFoundException("User not found");
    }

    public java.util.List<UserRecord> getAll() {
        return userRepository.findAll().stream().map(UserRecord::new).toList();
    }

    public UserRecord findByUsername(String username) throws NotFoundException, UnauthorizedException {
        var user = userRepository.findByUsername(username);

        if(user.isEmpty())
            throw new NotFoundException("User not found");
            
        if(AuthenticatedUser.getRole().equals("ADMIN") || AuthorizationService.isAuthenticatedUserOwnerOfResource(user.get().getUsername())) 
            return new UserRecord(user.get());

        return null;

    }
    
}
