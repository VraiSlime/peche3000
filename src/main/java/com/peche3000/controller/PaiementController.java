package com.peche3000.controller;

import com.peche3000.entity.Commande;
import com.peche3000.entity.Panier;
import com.peche3000.entity.Produit;
import com.peche3000.service.ClientService;
import com.peche3000.service.CommandeService;
import com.peche3000.service.PaiementService;
import com.peche3000.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private Panier panier;

    @PostMapping("/paiement")
    public String initierPaiement(Model model, Principal principal) {
        try {

            String email = principal.getName();

            // Créer une session Stripe
            String paiementUrl = paiementService.createPaymentSession(panier.getProduits(), email);

            // Rediriger vers la page Stripe
            return "redirect:" + paiementUrl;
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erreur", "Une erreur est survenue lors de l'initialisation du paiement.");
            return "panier";
        }
    }


    @GetMapping("/success")
    public String paiementReussi(@RequestParam("sessionId") String sessionId, Principal principal, Model model) {
        try {
            // Vérifie la session Stripe
            if (!paiementService.verifierSession(sessionId)) {
                model.addAttribute("erreur", "Le paiement n'a pas été validé.");
                return "error";
            }

            String email = principal.getName();
            List<Long> produitsIds = panier.getProduits().stream()
                    .map(Produit::getId)
                    .toList();
            Commande commande = commandeService.creerCommande(produitsIds, email);

            if (commande != null) {
                commandeService.viderPanier();
                System.out.println("Commande créée avec succès après paiement : " + commande);
                model.addAttribute("commande", commande);
                return "success";
            } else {
                model.addAttribute("erreur", "Une erreur est survenue lors de la création de la commande");
                return "error";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("erreur", "Une erreur inatendu est survenue");
            return "error";
        }
    }

    @GetMapping("/cancel")
    public String paiementAnnule() {
        return "cancel";
    }
}


