package com.viking.restcontrollers;


import com.viking.common.ERole;
import com.viking.common.JwtUtils;
import com.viking.dto.JwtResponse;
import com.viking.dto.LoginRequest;
import com.viking.dto.MessageResponse;
import com.viking.dto.SignupRequest;
import com.viking.entities.Account;
import com.viking.entities.Role;
import com.viking.repositories.RoleRepository;
import com.viking.repositories.UserRepository;
import com.viking.services.UserDetailsImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*", maxAge = 3600)
@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    HttpServletRequest request;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Validated @RequestBody LoginRequest loginRequest){
        if(loginRequest.getUsername() == null || loginRequest.getUsername().equals("")){
            return new ResponseEntity<MessageResponse>(new MessageResponse("  Vui lòng nhập username!"), HttpStatus.OK);
        }else if(loginRequest.getPassword() == null || loginRequest.getPassword().equals(""))
        {
            return new ResponseEntity<MessageResponse>(new MessageResponse("  Vui lòng nhập password!"), HttpStatus.OK);
        }else{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                            loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();
            List<String> roles = userDetailsImp.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            return ResponseEntity.ok(
                    new JwtResponse(jwt,
                            userDetailsImp.getId(),
                            userDetailsImp.getUsername(),
                            userDetailsImp.getEmail(),
                            roles)
            );
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Validated @RequestBody SignupRequest signupRequest){
        if (userRepository.existsByUsername(signupRequest.getUsername())){
            return ResponseEntity
                    .badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())){
            return ResponseEntity
                    .badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }


        Account user = new Account(signupRequest.getUsername(),
                            signupRequest.getEmail(),
                            encoder.encode(signupRequest.getPassword()));
        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null){
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }else{
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin" :
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;

                    case "mod" :
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);
                        break;

                    default :
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User resgiter successfully!"));
    }
}
