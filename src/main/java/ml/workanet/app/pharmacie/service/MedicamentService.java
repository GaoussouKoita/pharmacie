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
        auditService.ajouter(new Audit("Ajout Medicament",  medicament.getNom() + " "
                + " "+medicament.getPrixVente()));
        return repository.save(medicament);
    }

    public Medicament rechercher(Long id) {
        Medicament medicament = repository.findById(id).get();
        auditService.ajouter(new Audit("Recherche Medicament", medicament.getNom() + " "
                + " "+medicament.getPrixVente()));
        return medicament;
    }

    public void supprimer(Long id) {
        Medicament medicament = repository.findById(id).get();
        repository.deleteById(id);
        auditService.ajouter(new Audit("Suppression Medicament", medicament.getNom() + " "
                + " "+medicament.getPrixVente()));
    }

    public Page<Medicament> lister(int page, int nbreParPage) {
        auditService.ajouter(new Audit("Liste Medicaments", "Consultation"));
        return repository.findAll(PageRequest.of(page, nbreParPage,
                Sort.by("nom").ascending()));
    }

    public Page<Medicament> rechercher(String nom, int page, int nbreParPage) {
        auditService.ajouter(new Audit("Liste Medicaments", "Consultation"));
        return repository.findByNomContaining(nom, PageRequest.
                of(page, nbreParPage, Sort.by("nom").ascending()));
    }

    public List<Medicament> lister() {
        auditService.ajouter(new Audit("Liste Medicaments", "Consultation"));

        return repository.findAll(Sort.by("nom").descending());
    }
}
