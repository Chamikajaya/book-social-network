package com.chamika.books_project.config;

import com.chamika.books_project.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

// to provide the currently logged-in user for auditing purposes.
public class AppAuditAware implements AuditorAware<Integer> {  // Integer is the type of the user id

    @Override
    public Optional getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        User user = (User) authentication.getPrincipal();

        return Optional.ofNullable(user.getId());  // This id will be used as the createdBy and lastModifiedBy fields in the entities


    }
}
