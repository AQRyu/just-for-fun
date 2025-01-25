package com.aqryuz.backend.authentication.service;

import com.aqryuz.backend.authentication.controller.payload.LoginRequest;
import com.aqryuz.backend.authentication.controller.payload.LoginResponse;

public interface LoginService {

  public LoginResponse login(LoginRequest request);
}
