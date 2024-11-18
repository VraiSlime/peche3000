package com.peche3000.entity;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Component
@SessionScope
public class Panier {
    private List<Produit> produits = new ArrayList<>();

    public void ajouterProduit(Produit produit) {
        produits.add(produit);
    }

    public void supprimerProduit(Produit produit) {
        System.out.println("Avant suppression : " + produits.size());
        System.out.println("Tentative de suppression du produit : " + produit);
        produits.remove(produit);
        System.out.println("Après suppression : " + produits.size());
    }


    public List<Produit> getProduits() {
        return produits;
    }
}
