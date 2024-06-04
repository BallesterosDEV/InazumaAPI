package com.ballesteros.api.controllers;

import com.ballesteros.api.enums.Element;
import com.ballesteros.api.enums.HissatsuTechniquesType;
import com.ballesteros.api.persistence.models.HissatsuTechniquesModel;
import com.ballesteros.api.services.HissatsuTechniquesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HissatsuTechniquesController {

    @Autowired
    private HissatsuTechniquesService hissatsuTechniquesService;

    @GetMapping("/supertecnicas")
    public String showSupertécnicasForm(Model model) {
        model.addAttribute("hissatsuTechniques", new HissatsuTechniquesModel());
        model.addAttribute("types", HissatsuTechniquesType.values());
        return "supertecnicas";
    }

    @PostMapping("/supertécnicas")
    public String saveSupertécnicas(@ModelAttribute HissatsuTechniquesModel hissatsuTechniquesModel, Model model) {
        hissatsuTechniquesService.saveHissatsuTechniques(hissatsuTechniquesModel);
        return "redirect:/supertecnicas";
    }
}
