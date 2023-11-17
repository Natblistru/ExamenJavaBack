package edu.step.examenJavaRest.service;

import edu.step.examenJavaRest.model.User;
import edu.step.examenJavaRest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailServiceImpl implements CustomUserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userByEmail = userRepository.findUserByEmail(username);
        if(!userByEmail.isPresent()) {
            throw new UsernameNotFoundException("User not found!");
        }
        return userByEmail.get();
    }

    public User loadUser(String username) {
        return userRepository.findUserByEmail(username)
                .orElse(null);
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
