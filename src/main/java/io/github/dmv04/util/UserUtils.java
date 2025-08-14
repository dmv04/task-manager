package io.github.dmv04.util;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import io.github.dmv04.exception.ResourceNotFoundException;
import io.github.dmv04.model.User;
import io.github.dmv04.repository.UserRepository;

@Component
@AllArgsConstructor
public class UserUtils {
    private final UserRepository userRepository;
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        var email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User with email " + email + " not found"));
    }
    public boolean isOwner(Long userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + userId + " not found"));
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return user.getEmail().equals(authentication.getName());
    }
    public User getTestUser() {
        return userRepository.findByEmail("dmv04@example.com")
                .orElseThrow(() -> new RuntimeException("User doesn't exist"));
    }
}
