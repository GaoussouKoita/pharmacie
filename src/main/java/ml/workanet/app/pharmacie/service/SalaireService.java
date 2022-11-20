package ml.workanet.app.pharmacie.service;

import ml.workanet.app.pharmacie.domaine.Audit;
import ml.workanet.app.pharmacie.domaine.Depense;
import ml.workanet.app.pharmacie.domaine.Salaire;
import ml.workanet.app.pharmacie.repository.SalaireRepository;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaireService {

    @Autowired
    private SalaireRepository repository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private DepenseService depenseService;
    @Autowired
    private AuditService auditService;


    public Salaire ajouter(Salaire salaire) {

        salaire.setPharmacie(accountService.utilisateurActif().getPharmacie());
        salaire.setUtilisateur(accountService.rechercher(salaire.getUtilisateur().getId()));
        depenseService.ajouter(new Depense(salaire.getMontant(), "SALAIRE",
                salaire.getUtilisateur().getPrenom() + " " +
                        salaire.getUtilisateur().getNom() + " " +
                        salaire.getMois()));
        auditService.ajouter(new Audit("Ajout Salaire", salaire.getMontant() + " "
                + " " + salaire.getMontant() + " " + salaire.getUtilisateur().getEmail()));
        return repository.save(salaire);
    }

    public Salaire rechercher(Long id) {
        Salaire salaire = repository.findById(id).get();
        auditService.ajouter(new Audit("Recherche Salaire", salaire.getMontant() + " "
                + " " + salaire.getMontant() + " " + salaire.getUtilisateur().getEmail()));
        return salaire;
    }

    public void supprimer(Long id) {
        Salaire salaire = repository.findById(id).get();
        repository.deleteById(id);
        auditService.ajouter(new Audit("Suppression Salaire", salaire.getMontant() + " "
                + " " + salaire.getMontant() + " " + salaire.getUtilisateur().getEmail()));
    }

    public Page<Salaire> lister(int page, int nbreParPage) {
        auditService.ajouter(new Audit("Liste Salaires", "Consultation"));
        return repository.findByPharmacie(accountService.utilisateurActif().getPharmacie(),
                PageRequest.of(page, nbreParPage, Sort.by("mois").descending()));
    }

    public Page<Salaire> rechercher(String nom, int page, int nbreParPage) {
        auditService.ajouter(new Audit("Liste Salaires", "Consultation"));
        return repository.findByUtilisateurNomContainingAndPharmacie(nom, accountService.utilisateurActif().getPharmacie(),
                PageRequest.of(page, nbreParPage));
    }

    public List<Salaire> lister() {
        auditService.ajouter(new Audit("Liste Salaires", "Consultation"));
        return repository.findByPharmacie(accountService.utilisateurActif().getPharmacie());
    }
}
