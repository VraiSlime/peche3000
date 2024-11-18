package com.peche3000.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String prenom;
    private String email;

    @Column(nullable = false)
    private String motDePasse;

    @Column(nullable = false)
    private String role;

    @Column
    private String adresse;

    @Column
    private String telephone;

    @ManyToMany(mappedBy = "participants")
    private List<Concours> concours;

    public List<Concours> getConcours() {
        return concours;
    }

    @OneToMany(mappedBy = "client")
    private List<PermisDePeche> permisDePeches;

    @OneToMany(mappedBy = "client")
    @JsonBackReference
    private List<Commande> commandes;

    public Client() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMotDePasse() { return motDePasse; }
    public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setConcours(List<Concours> concours) {
        this.concours = concours;
    }

    public List<PermisDePeche> getPermisDePeches() {
        return permisDePeches;
    }

    public void setPermisDePeches(List<PermisDePeche> permisDePeches) {
        this.permisDePeches = permisDePeches;
    }
}
