package com.example.HospitaManagmentSystemDemo.security;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CurrentUserProvider {

    /**
     * Retrieves the current user's ID.
     * Hardcoded to 1L for now until Spring Security is fully integrated with a UserDetails object.
     * @return Optional containing the user ID.
     */
    public Optional<Long> getCurrentUserId() {
        // TODO: Replace with SecurityContextHolder.getContext().getAuthentication() logic
        return Optional.of(1L);
    }
}
