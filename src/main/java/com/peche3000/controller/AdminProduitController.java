package com.peche3000.controller;

import com.peche3000.entity.Produit;
import com.peche3000.service.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/produits")
public class AdminProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping("/view")
    public String viewProduitsAdmin(Model model) {
        model.addAttribute("produits", produitService.findAll());
        return "produits_admin"; // Vue pour l'administration
    }

    @GetMapping("/add")
    public String showAddProduitForm(Model model) {
        model.addAttribute("produit", new Produit());
        return "add_produit";
    }

    @PostMapping("/add")
    public String addProduit(@ModelAttribute Produit produit) {
        produitService.save(produit);
        return "redirect:/admin/produits/view";
    }

    @GetMapping("/edit/{id}")
    public String showEditProduitForm(@PathVariable Long id, Model model) {
        Produit produit = produitService.findById(id);
        if (produit == null) {
            return "redirect:/admin/produits/view"; // Rediriger si le produit n'existe pas
        }
        model.addAttribute("produit", produit);
        return "edit_produit";
    }

    @PostMapping("/edit/{id}")
    public String editProduit(@PathVariable Long id, @ModelAttribute Produit produitDetails) {
        Produit produit = produitService.findById(id);
        if (produit != null) {
            produit.setNom(produitDetails.getNom());
            produit.setDescription(produitDetails.getDescription());
            produit.setPrix(produitDetails.getPrix());
            produit.setCategorie(produitDetails.getCategorie());
            produit.setStock(produitDetails.getStock());
            produit.setImageUrl(produitDetails.getImageUrl());
            produitService.save(produit);
        }
        return "redirect:/admin/produits/view";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduit(@PathVariable Long id) {
        produitService.deleteById(id);
        return "redirect:/admin/produits/view";
    }
}
