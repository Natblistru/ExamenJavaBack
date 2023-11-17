package edu.step.examenJavaRest.controller;

import edu.step.examenJavaRest.config.JwtTokenService;
import edu.step.examenJavaRest.dto.LoginDTO;
import edu.step.examenJavaRest.dto.LoginResponseDTO;
import edu.step.examenJavaRest.dto.RegisterDTO;
import edu.step.examenJavaRest.model.Role;
import edu.step.examenJavaRest.model.User;
import edu.step.examenJavaRest.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SecurityController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtTokenService jwtTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();

        try{
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException ex) {
            return ResponseEntity.badRequest().body(null);
        } catch (BadCredentialsException ex) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        User user = userDetailsService.loadUser(username);
        String token = jwtTokenService.generateJwtToken(userDetails);

        return ResponseEntity.ok(new LoginResponseDTO(token, user.getFirstName(), user.getLastName()));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) { // DTO = data transfer object
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("USER"));

        userDetailsService.save(
                new User(
                        registerDTO.getFirstName(),
                        registerDTO.getLastName(),
                        registerDTO.getUsername(),
                        passwordEncoder.encode(registerDTO.getPassword()),
                        roles
                ));

        return new ResponseEntity<>("User created", HttpStatus.CREATED);


    }
}
