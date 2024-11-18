package com.peche3000.controller;

import com.peche3000.entity.Panier;
import com.peche3000.entity.Produit;
import com.peche3000.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/produits")
public class UserProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping
    public String viewProduitsUtilisateur(Model model) {
        model.addAttribute("produits", produitService.findAll());
        return "produits_utilisateur";
    }

    @GetMapping("/{id}")
    public String viewProduitDetails(@PathVariable Long id, Model model) {
        Produit produit = produitService.findById(id);
        model.addAttribute("produit", produit);
        return "produit_detail";
    }

    @GetMapping("/categories")
    public String viewProduitsParCategorie(@RequestParam("categorie") String categorie, Model model) {
        List<Produit> produits = produitService.findByCategorie(categorie);
        model.addAttribute("produits", produits);
        model.addAttribute("categorie", categorie);
        return "produits_par_categorie";
    }

    @GetMapping("/search")
    public String searchProduits(@RequestParam("query") String query, Model model) {
        List<Produit> produits = produitService.searchByNom(query);
        model.addAttribute("produits", produits);
        model.addAttribute("query", query);
        return "produits_recherche";
    }

    @Autowired
    private Panier panier;

    @PostMapping("/panier/ajouter/{id}")
    public String ajouterAuPanier(@PathVariable Long id) {
        Produit produit = produitService.findById(id);
        if (produit != null) {
            panier.ajouterProduit(produit);
        }
        return "redirect:/produits";
    }

    @GetMapping("/panier")
    public String voirPanier(Model model) {
        model.addAttribute("produits", panier.getProduits());
        return "panier";
    }

    @PostMapping("/panier/supprimer/{id}")
    public String retirerDuPanier(@PathVariable Long id) {
        Produit produit = produitService.findById(id);
        if (produit != null) {
            panier.supprimerProduit(produit);
        }
        return "redirect:/produits/panier";
    }





}
