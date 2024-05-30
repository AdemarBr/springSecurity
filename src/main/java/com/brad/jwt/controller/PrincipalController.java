package com.brad.jwt.controller;

import com.brad.jwt.controller.request.CreateUserDTO;
import com.brad.jwt.models.ERole;
import com.brad.jwt.models.RoleEntity;
import com.brad.jwt.models.UserEntity;
import com.brad.jwt.repositories.RoleRepository;
import com.brad.jwt.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class PrincipalController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/hello")
    public String hello(){
        return  "Hello word not secured";
    }

    @GetMapping("/helloSecured")
    public String helloSecured(){
        return  "Hello word secured";
    }

    /*@PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){

        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                        .name(ERole.valueOf(role))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(createUserDTO.getPassword())
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();
        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);

    }*/

    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDTO createUserDTO){

        List<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(role -> {
                    ERole eRole = ERole.valueOf(role);
                    return roleRepository.findByName(eRole)
                            .orElseGet(() -> {
                                RoleEntity newRole = RoleEntity.builder().name(eRole).build();
                                return roleRepository.save(newRole);
                            });
                })
                .collect(Collectors.toList());


        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(createUserDTO.getPassword())
                .email(createUserDTO.getEmail())
                .roles(roles)
                .build();

        userRepository.save(userEntity);

        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/delete")
    public String deleteuser(@RequestParam String id){
        userRepository.deleteById(Long.parseLong(id));
        return "Sea borrado el user con id".concat(id);

    }
}
