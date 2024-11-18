package com.peche3000.service;

import com.peche3000.entity.Client;
import com.peche3000.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService implements UserDetailsService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Client save(Client client) {
        client.setMotDePasse(passwordEncoder.encode(client.getMotDePasse()));
        return clientRepository.save(client);
    }

    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client non trouvé pour l'ID : " + id));
    }

    public void deleteById(Long id) {
        if (!clientRepository.existsById(id)) {
            throw new RuntimeException("Impossible de supprimer : Client avec l'ID " + id + " n'existe pas.");
        }
        clientRepository.deleteById(id);
    }

    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client non trouvé pour l'email : " + email));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        System.out.println("Tentative de connexion pour l'email : " + email);

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Client non trouvé pour l'email : " + email));

        System.out.println("Utilisateur trouvé : " + client.getNom() + " (" + client.getRole() + ")");

        return new org.springframework.security.core.userdetails.User(
                client.getEmail(),
                client.getMotDePasse(),
                Collections.singletonList(new SimpleGrantedAuthority(client.getRole()))
        );
    }

    public boolean isEmailExist(String email) {
        return clientRepository.findByEmail(email).isPresent();
    }
}
