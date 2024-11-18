package com.peche3000.controller;

import com.peche3000.entity.Client;
import com.peche3000.entity.Concours;
import com.peche3000.service.ClientService;
import com.peche3000.service.ConcoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/concours")
public class ConcoursController {

    @Autowired
    private ConcoursService concoursService;

    @Autowired
    private ClientService clientService;

    @GetMapping
    public String listConcours(Model model, Principal principal) {
        System.out.println("Principal : " + (principal != null ? principal.getName() : "Aucun utilisateur connecté"));

        model.addAttribute("concours", concoursService.findAll());
        if (principal != null) {
            Client client = clientService.findByEmail(principal.getName());
            System.out.println("Client trouvé : " + (client != null ? client.getId() : "Aucun client trouvé"));
            model.addAttribute("clientId", client != null ? client.getId() : null);
        } else {
            model.addAttribute("clientId", null);
        }
        return "concours_list";
    }


    // Affiche la gestion des concours pour l'administrateur
    @GetMapping("/admin")
    public String adminConcours(Model model) {
        model.addAttribute("concours", concoursService.findAll());
        return "concours_admin";
    }

    // Affiche le formulaire pour créer  concours
    @GetMapping("/create")
    public String createConcoursForm(Model model) {
        model.addAttribute("concours", new Concours());
        return "concours_form";
    }

    // Traite le formulaire pour créer concours
    @PostMapping("/create")
    public String createConcours(@ModelAttribute Concours concours) {
        concoursService.save(concours);
        return "redirect:/concours/admin";
    }

    // Affiche le formulaire pour modifier un concours existant
    @GetMapping("/edit/{id}")
    public String editConcoursForm(@PathVariable Long id, Model model) {
        Concours concours = concoursService.findById(id);
        model.addAttribute("concours", concours);
        return "concours_form";
    }

    // Traite le formulaire pour modifier un concours existant
    @PostMapping("/edit/{id}")
    public String editConcours(@PathVariable Long id, @ModelAttribute Concours concoursDetails) {
        Concours concours = concoursService.findById(id);
        if (concours != null) {
            concours.setNom(concoursDetails.getNom());
            concours.setDate(concoursDetails.getDate());
            concours.setLieu(concoursDetails.getLieu());
            concours.setDescription(concoursDetails.getDescription());
            concoursService.save(concours);
        }
        return "redirect:/concours/admin";
    }

    @PostMapping("/delete/{id}")
    public String deleteConcours(@PathVariable Long id) {
        concoursService.deleteById(id);
        return "redirect:/concours/admin";
    }

    @PostMapping("/{id}/inscription")
    public String inscrireParticipant(@PathVariable Long id, @RequestParam("clientId") Long clientId) {
        if (clientId == null || id == null) {
            throw new IllegalArgumentException("ID du concours ou ID du client manquant");
        }

        concoursService.inscrireParticipant(id, clientId);
        return "redirect:/concours";
    }


    @GetMapping("/mes-concours")
    public String mesConcours(Model model, Principal principal) {
        if (principal != null) {
            Client client = clientService.findByEmail(principal.getName());
            if (client != null) {
                model.addAttribute("concours", client.getConcours());
            }
        }
        return "mes_concours";
    }


}
