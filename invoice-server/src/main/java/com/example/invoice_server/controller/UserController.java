package com.example.invoice_server.controller;

import com.example.invoice_server.dto.UserRequestDTO;
import com.example.invoice_server.dto.UserResponseDTO;
import com.example.invoice_server.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService=userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO request){

        UserResponseDTO response=userService.registerUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    
}
