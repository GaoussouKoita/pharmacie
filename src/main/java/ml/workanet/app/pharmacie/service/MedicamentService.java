package ml.workanet.app.pharmacie.service;

import ml.workanet.app.pharmacie.domaine.Audit;
import ml.workanet.app.pharmacie.domaine.Medicament;
import ml.workanet.app.pharmacie.repository.MedicamentRepository;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicamentService {

    @Autowired
    private MedicamentRepository repository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuditService auditService;
    @Autowired
    private TypeService typeService;

    public Medicament ajouter(Medicament medicament) {

        medicament.setType(typeService.rechercher(medicament.getType().getId()));
        medicament.setPharmacie(accountService.utilisateurActif().getPharmacie());
        auditService.ajouter(new Audit("Ajout Medicament", medicament.getNom() + " "
                + " " + medicament.getPrixVente()));
        return repository.save(medicament);
    }

    public List<Medicament> ajouter(List<Medicament> medicaments) {

        List<Medicament> medicaments1 = new ArrayList<>();
        for (Medicament medicament : medicaments) {
            medicaments1.add(listerParNom(medicament.getId()));
        }
        return repository.saveAll(medicaments);
    }

    public Medicament listerParNom(Long id) {
        Medicament medicament = repository.findById(id).get();
        auditService.ajouter(new Audit("Recherche Medicament", medicament.getNom() + " "
                + " " + medicament.getPrixVente()));
        return medicament;
    }

    public Medicament listerParNom(String nom) {
        Medicament medicament = repository.findByNomAndPharmacie(nom,
                accountService.utilisateurActif().getPharmacie());
        auditService.ajouter(new Audit("Recherche Medicament",
                medicament.getNom() + " "
                        + " " + medicament.getPrixVente()));
        return medicament;
    }

    public void supprimer(Long id) {
        Medicament medicament = repository.findById(id).get();
        repository.deleteById(id);
        auditService.ajouter(new Audit("Suppression Medicament", medicament.getNom() + " "
                + " " + medicament.getPrixVente()));
    }

    public Page<Medicament> lister(int page, int nbreParPage) {
        auditService.ajouter(new Audit("Liste Medicaments", "Consultation"));
        return repository.findByPharmacie(accountService.utilisateurActif().getPharmacie(),
                PageRequest.of(page, nbreParPage, Sort.by("nom").ascending()));
    }

    public Page<Medicament> listerParNom(String nom, int page, int nbreParPage) {
        auditService.ajouter(new Audit("Liste Medicaments", "Consultation"));
        return repository.findByNomContainingAndPharmacie(nom, accountService.utilisateurActif().getPharmacie(),
                PageRequest.of(page, nbreParPage, Sort.by("nom").ascending()));
    }

    public List<Medicament> lister() {
        auditService.ajouter(new Audit("Liste Medicaments", "Consultation"));

        return repository.findByPharmacie(accountService.utilisateurActif().getPharmacie(),
                Sort.by("nom").descending());
    }
}
