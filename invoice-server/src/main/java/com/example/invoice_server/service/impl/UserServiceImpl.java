package com.example.invoice_server.service.impl;

import com.example.invoice_server.dto.UserRequestDTO;
import com.example.invoice_server.dto.UserResponseDTO;
import com.example.invoice_server.entity.User;
import com.example.invoice_server.exception.EmailAlreadyExistsException;
import com.example.invoice_server.repository.UserRepository;
import com.example.invoice_server.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserResponseDTO registerUser(UserRequestDTO request){
        if(userRepository.existsByEmail(request.getEmail())){
            throw new EmailAlreadyExistsException("Email Already Exists");
        }

        User user=new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User savedUser=userRepository.save(user);

        UserResponseDTO response = new UserResponseDTO();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());
        response.setCreatedAt(savedUser.getCreatedAt());

        return response;

    }
}
