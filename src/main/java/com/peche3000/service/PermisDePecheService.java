package com.peche3000.service;

import com.peche3000.entity.Client;
import com.peche3000.entity.PermisDePeche;
import com.peche3000.repository.ClientRepository;
import com.peche3000.repository.PermisDePecheRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PermisDePecheService {

    @Autowired
    private PermisDePecheRepository permisDePecheRepository;

    @Autowired
    private ClientRepository clientRepository;

    public PermisDePeche createPermis(String typePermis, String motif, LocalDate dateSouhaitee, String email) {
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client non trouvé : " + email));

        PermisDePeche permis = new PermisDePeche();
        permis.setDateDemande(LocalDate.now());
        permis.setTypePermis(typePermis);
        permis.setMotif(motif);
        permis.setDateSouhaitee(dateSouhaitee);
        permis.setStatut("EN_ATTENTE");
        permis.setClient(client);

        return permisDePecheRepository.save(permis);
    }

    public List<PermisDePeche> findByClientEmail(String email) {
        try {
            System.out.println("Requête JPA pour trouver les permis avec email : " + email);
            List<PermisDePeche> permis = permisDePecheRepository.findByClientEmail(email);
            System.out.println("Résultat de la requête JPA : " + permis);
            return permis;
        } catch (Exception e) {
            System.err.println("Erreur dans findByClientEmail : " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    public void changerStatut(Long id, String statut) {
        PermisDePeche demande = permisDePecheRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Demande introuvable : " + id));
        demande.setStatut(statut);
        permisDePecheRepository.save(demande);
    }

    public List<PermisDePeche> findAll() {
        return permisDePecheRepository.findAll();
    }

    public long countPermis() {
        return permisDePecheRepository.count();
    }
}
