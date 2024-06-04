package com.ballesteros.api.services;

import com.ballesteros.api.persistence.models.UserModel;
import com.ballesteros.api.persistence.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

}
