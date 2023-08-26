package com.ecureuill.ada.avanade.orderapi.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticatedUser {
    public static boolean isAuthenticated() {
        return SecurityContextHolder.getContext().getAuthentication()!= null;
    }

    public static String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public static String getRole() {
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
    }



}