package com.peche3000.controller;

import org.springframework.ui.Model;
import com.peche3000.entity.Client;
import com.peche3000.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @GetMapping
    @ResponseBody
    public List<Client> getAllClients() {
        return clientService.findAll();
    }


    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        Client client = clientService.findById(id);
        if (client != null) {
            return ResponseEntity.ok(client);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        if (clientService.isEmailExist(client.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Client newClient = clientService.save(client);
        return ResponseEntity.status(HttpStatus.CREATED).body(newClient);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client clientDetails) {
        Client client = clientService.findById(id);
        if (client != null) {
            client.setNom(clientDetails.getNom());
            client.setPrenom(clientDetails.getPrenom());
            client.setEmail(clientDetails.getEmail());
            client.setMotDePasse(clientDetails.getMotDePasse());
            Client updatedClient = clientService.save(client);
            return ResponseEntity.ok(updatedClient);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        if (clientService.findById(id) != null) {
            clientService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("client", new Client());
        return "register"; // Rendre la vue Thymeleaf "register.html"
    }

    // Logique pour enregistrer un client
    @PostMapping("/register")
    public String registerClient(@ModelAttribute Client client, Model model) {
        if (clientService.isEmailExist(client.getEmail())) {
            model.addAttribute("error", "Cet email est déjà utilisé.");
            return "register";
        }
        client.setRole("ROLE_USER"); // role par défaut
        clientService.save(client);
        return "redirect:/login";
    }

    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        Client client = clientService.findByEmail(principal.getName()); // Récupère l'utilisateur connecté
        model.addAttribute("client", client);
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute Client clientDetails, Principal principal) {
        Client client = clientService.findByEmail(principal.getName());
        if (client != null) {
            client.setNom(clientDetails.getNom());
            client.setPrenom(clientDetails.getPrenom());
            client.setAdresse(clientDetails.getAdresse());
            client.setTelephone(clientDetails.getTelephone());
            clientService.save(client);
        }
        return "redirect:/clients/profile";
    }
}
