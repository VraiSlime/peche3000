package com.peche3000.entity;

import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.List;

@Entity
public class Concours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private LocalDate date;
    private String lieu;
    private String description;

    @ManyToMany
    private List<Client> participants;

    public Concours() {
    }

    public Concours(String nom, LocalDate date, String lieu, String description) {
        this.nom = nom;
        this.date = date;
        this.lieu = lieu;
        this.description = description;
    }

    // Getters et setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Client> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Client> participants) {
        this.participants = participants;
    }
}
