package com.peche3000.controller;

import com.peche3000.service.CommandeService;
import com.peche3000.service.ConcoursService;
import com.peche3000.service.PermisDePecheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private PermisDePecheService permisDePecheService;

    @Autowired
    private ConcoursService concoursService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("totalVentes", commandeService.countVentes());
        model.addAttribute("totalPermis", permisDePecheService.countPermis());
        model.addAttribute("totalInscriptions", concoursService.countInscriptions());
        return "admin_dashboard";
    }
}
