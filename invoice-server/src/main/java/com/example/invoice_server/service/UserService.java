package com.example.invoice_server.service;

import com.example.invoice_server.dto.UserRequestDTO;
import com.example.invoice_server.dto.UserResponseDTO;

public interface UserService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);
}
