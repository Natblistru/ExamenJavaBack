package edu.step.examenJavaRest.service;

import edu.step.examenJavaRest.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface CustomUserDetailsService extends UserDetailsService {
    void save(User user);
    User loadUser(String username);
}

