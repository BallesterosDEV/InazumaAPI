package com.ballesteros.api.services;

import com.ballesteros.api.persistence.models.RolModel;
import com.ballesteros.api.persistence.repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public void saveRol(RolModel rol) {
        rolRepository.save(rol);
    }

}
