package com.peche3000.controller;

import com.peche3000.entity.PermisDePeche;
import com.peche3000.service.PermisDePecheService;
import com.peche3000.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/permis")
public class PermisDePecheController {

    @Autowired
    private PermisDePecheService permisDePecheService;

    @Autowired
    private ClientService clientService;

    // Utilisateur

    @GetMapping("/mes_demandes")
    public String afficherMesDemandes(Model model, Principal principal) {
        try {
            String email = principal.getName();
            List<PermisDePeche> mesDemandes = permisDePecheService.findByClientEmail(email);
            model.addAttribute("mesDemandes", mesDemandes);
            return "mes_demandes_permis";
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @GetMapping("/nouveau")
    public String afficherFormulaireDemande(Model model) {
        model.addAttribute("demande", new PermisDePeche());
        return "nouveau_permis";
    }

    @PostMapping("/nouveau")
    public String creerPermis(
            @RequestParam String typePermis,
            @RequestParam String motif,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateSouhaitee,
            Principal principal) {

        String email = principal.getName();
        permisDePecheService.createPermis(typePermis, motif, dateSouhaitee, email);

        return "redirect:/permis/mes_demandes";
    }

    // Admin

    @GetMapping("/admin")
    public String afficherDemandesAdmin(Model model) {
        List<PermisDePeche> demandes = permisDePecheService.findAll();
        model.addAttribute("demandes", demandes);
        return "admin_demandes_permis";
    }

    @PostMapping("/{id}/statut")
    public String changerStatut(
            @PathVariable Long id,
            @RequestParam String statut) {

        permisDePecheService.changerStatut(id, statut);
        return "redirect:/permis/admin";
    }
}
