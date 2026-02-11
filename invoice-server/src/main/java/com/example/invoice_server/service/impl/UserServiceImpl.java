package com.example.invoice_server.service.impl;

import com.example.invoice_server.dto.UserRequestDTO;
import com.example.invoice_server.dto.UserResponseDTO;
import com.example.invoice_server.entity.User;
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
    public UserResponseDTO createUser(UserRequestDTO requestDTO) {

        //dto to entity

        User user=new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());

        User savedUser=userRepository.save(user);

        //entity to dto

        UserResponseDTO responseDTO=new UserResponseDTO();
        responseDTO.setId(savedUser.getId());
        responseDTO.setName(savedUser.getName());
        responseDTO.setEmail(savedUser.getEmail());
        responseDTO.setCreatedAt(savedUser.getCreatedAt());

        return responseDTO;
    }
}
