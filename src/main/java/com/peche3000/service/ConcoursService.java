package com.peche3000.service;

import com.peche3000.entity.Client;
import com.peche3000.entity.Concours;
import com.peche3000.repository.ConcoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcoursService {

    @Autowired
    private ConcoursRepository concoursRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailService emailService;

    public Concours save(Concours concours) {
        return concoursRepository.save(concours);
    }

    public List<Concours> findAll() {
        return concoursRepository.findAll();
    }

    public Concours findById(Long id) {
        return concoursRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        concoursRepository.deleteById(id);
    }

    public void inscrireParticipant(Long concoursId, Long clientId) {
        Concours concours = concoursRepository.findById(concoursId)
                .orElseThrow(() -> new IllegalArgumentException("Concours introuvable avec l'ID : " + concoursId));
        Client client = clientService.findById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client introuvable avec l'ID : " + clientId);
        }

        List<Client> participants = concours.getParticipants();
        if (!participants.contains(client)) {
            participants.add(client);
            concours.setParticipants(participants);
            concoursRepository.save(concours);

            // Envoi d'email qui marche pas ;(
            String subject = "Confirmation d'inscription : " + concours.getNom();
            String body = String.format("Bonjour %s,\n\nVous êtes inscrit avec succès au concours \"%s\" prévu le %s à %s.\n\nBonne chance !",
                    client.getNom(), concours.getNom(), concours.getDate(), concours.getLieu());
            emailService.sendEmail(client.getEmail(), subject, body);
        }
    }

    public long countInscriptions() {
        return concoursRepository.countInscriptions();
    }


}
