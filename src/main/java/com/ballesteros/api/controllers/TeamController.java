package com.ballesteros.api.controllers;

import com.ballesteros.api.enums.Country;
import com.ballesteros.api.persistence.models.TeamModel;
import com.ballesteros.api.services.TeamService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/create-team")
    public String showCreateForm(Model model) {
        model.addAttribute("team", new TeamModel());
        model.addAttribute("countries", Country.values());
        return "create-team";
    }

    @PostMapping("/save-team")
    @ResponseBody
    @Hidden
    public String saveTeam(@ModelAttribute TeamModel teamModel, @RequestParam("file") MultipartFile image) {
        try {
            teamService.saveTeam(teamModel, image);
            return "Team saved successfully";
        } catch (Exception e) {
            return "Failed to save team: " + e.getMessage();
        }
    }

}
