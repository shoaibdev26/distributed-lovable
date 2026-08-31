package com.irusol.distributed_lovable.account_service.service;


import com.irusol.distributed_lovable.account_service.dto.auth.AuthResponse;
import com.irusol.distributed_lovable.account_service.dto.auth.LoginRequest;
import com.irusol.distributed_lovable.account_service.dto.auth.SignupRequest;

public interface AuthService {
    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}
