package com.peche3000.controller;

import com.peche3000.entity.Commande;
import com.peche3000.entity.Produit;
import com.peche3000.service.CommandeService;
import com.peche3000.service.ClientService;
import com.peche3000.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private ProduitService produitService;

    @Autowired
    private ClientService clientService;

    // UTILISATEUR

    @GetMapping
    public String voirCommandesUtilisateur(Model model, Principal principal) {
        String email = principal.getName(); // Récupère l'email de l'utilisateur connecté
        System.out.println("Email utilisateur connecté : " + email);
        List<Commande> commandes = commandeService.findByClientEmail(email);
        model.addAttribute("commandes", commandes);
        return "commandes_utilisateur";
    }

    @PostMapping("/creer")
    public String creerCommande(@RequestParam List<Long> produitsIds, Principal principal) {
        String email = principal.getName();
        Commande commande = commandeService.creerCommande(produitsIds, email);
        if (commande != null) {
            return "redirect:/commandes";
        }
        return "redirect:/error";
    }

    //  ADMINI

    @GetMapping("/admin")
    public String voirToutesLesCommandes(Model model) {
        List<Commande> commandes = commandeService.findAll();
        model.addAttribute("commandes", commandes);
        return "commandes_admin";
    }

    @PostMapping("/admin/{id}/modifier")
    public String modifierStatutCommande(@PathVariable Long id, @RequestParam String statut) {
        Commande commande = commandeService.findById(id);
        if (commande != null) {
            commande.setStatut(statut);
            commandeService.save(commande);
        }
        return "redirect:/commandes/admin";
    }
}
