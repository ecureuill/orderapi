package com.ecureuill.ada.avanade.orderapi.service;

import com.ecureuill.ada.avanade.orderapi.exceptions.UnauthorizedException;
import com.ecureuill.ada.avanade.orderapi.utils.AuthenticatedUser;

public class AuthorizationService {
    public static boolean isAuthenticatedUserOwnerOfResource(String username) throws UnauthorizedException {
        if(AuthenticatedUser.getUsername().equals(username)) {
            return true;
        }

        throw new UnauthorizedException();
    }
}
