package com.peche3000.service;

import com.peche3000.entity.Produit;
import com.peche3000.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProduitService {

    @Autowired
    private ProduitRepository produitRepository;

    public Produit save(Produit produit) {
        return produitRepository.save(produit);
    }

    public List<Produit> findAll() {
        return produitRepository.findAll();
    }

    public Produit findById(Long id) {
        Produit produit = produitRepository.findById(id).orElse(null);
        System.out.println("Produit récupéré : " + produit);
        return produit;
    }


    public List<Produit> findByCategorie(String categorie) {
        return produitRepository.findByCategorie(categorie);
    }

    public void deleteById(Long id) {
        produitRepository.deleteById(id);
    }
    public List<Produit> searchByNom(String nom) {
        return produitRepository.findByNomContaining(nom);
    }

}
