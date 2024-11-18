package com.peche3000.service;

import com.peche3000.entity.Client;
import com.peche3000.entity.Commande;
import com.peche3000.entity.Panier;
import com.peche3000.entity.Produit;
import com.peche3000.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommandeService {

    @Autowired
    private Panier panier;

    @Autowired
    private ProduitService produitService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private CommandeRepository commandeRepository;

    // Crée une commande pour un client à partir des IDs de produits
    public Commande creerCommande(List<Long> produitsIds, String clientEmail) {
        Client client = clientService.findByEmail(clientEmail);
        if (client == null) {
            throw new IllegalArgumentException("Client introuvable pour l'email : " + clientEmail);
        }

        List<Produit> produits = produitsIds.stream()
                .map(produitService::findById)
                .filter(produit -> produit != null)
                .toList();

        if (produits.isEmpty()) {
            throw new IllegalArgumentException("Aucun produit trouvé pour les IDs : " + produitsIds);
        }

        Commande commande = new Commande();
        commande.setClient(client);
        commande.setProduits(produits);
        commande.setDateCommande(LocalDate.now());
        commande.setStatut("En cours");

        return commandeRepository.save(commande);
    }

    public void viderPanier() {
        panier.getProduits().clear();
        System.out.println("Panier vidé après la création de la commande.");
    }

    public Commande findById(Long id) {
        return commandeRepository.findById(id).orElse(null);
    }

    public List<Commande> findAll() {
        return commandeRepository.findAll();
    }

    public List<Commande> findByClientEmail(String email) {
        return commandeRepository.findByClient_Email(email);
    }

    public Commande save(Commande commande) {
        return commandeRepository.save(commande);
    }

    public long countVentes() {
        return commandeRepository.count();
    }
}
