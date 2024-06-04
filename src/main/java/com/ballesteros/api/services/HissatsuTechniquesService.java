package com.ballesteros.api.services;

import com.ballesteros.api.persistence.models.HissatsuTechniquesModel;
import com.ballesteros.api.persistence.repositories.HissatsuTechniquesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar HissatsuTechniquesModel.
 */
@Service
public class HissatsuTechniquesService {
    @Autowired
    private HissatsuTechniquesRepository hissatsuTechniquesRepository;

    /**
     * Obtiene todas las técnicas Hissatsu.
     *
     * @return una lista de todas las técnicas Hissatsu
     */
    public List<HissatsuTechniquesModel> getAllHissatsuTechniques() {
        return hissatsuTechniquesRepository.findAll();
    }

    /**
     * Guarda una técnica Hissatsu.
     *
     * @param hissatsuTechniquesModel el modelo de la técnica a guardar
     */
    public void saveHissatsuTechniques(HissatsuTechniquesModel hissatsuTechniquesModel) {
        hissatsuTechniquesRepository.save(hissatsuTechniquesModel);
    }

    /**
     * Obtiene una técnica Hissatsu por su ID.
     *
     * @param id el ID de la técnica
     * @return la técnica Hissatsu con el ID dado, o null si no se encuentra
     */
    public HissatsuTechniquesModel getTechniqueById(Long id) {
        Optional<HissatsuTechniquesModel> techniqueOptional = hissatsuTechniquesRepository.findById(id);
        return techniqueOptional.orElse(null);
    }

    /**
     * Obtiene una técnica Hissatsu por su nombre.
     *
     * @param name el nombre de la técnica
     * @return la técnica Hissatsu con el nombre dado, o null si no se encuentra
     */
    public HissatsuTechniquesModel getTechniqueByName(String name) {
        return hissatsuTechniquesRepository.findByName(name);
    }
}
