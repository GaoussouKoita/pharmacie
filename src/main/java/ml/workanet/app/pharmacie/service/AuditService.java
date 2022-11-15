package ml.workanet.app.pharmacie.service;

import ml.workanet.app.pharmacie.domaine.Audit;
import ml.workanet.app.pharmacie.repository.AuditRepository;
import ml.workanet.app.pharmacie.securite.entity.Utilisateur;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    @Autowired
    private AuditRepository repository;
    @Autowired
    private AccountService accountService;

    public Audit ajouter(Audit audit){
        audit.setPharmacie(accountService.utilisateurActif().getPharmacie());
        audit.setUtilisateur(accountService.utilisateurActif());
        return repository.save(audit);
    }

    public Page<Audit> lister(Long id, int page) {
        Utilisateur utilisateur = accountService.rechercher(id);
        ajouter(new Audit("Liste Utilisateur : "+ utilisateur.getEmail()+" : "
                +utilisateur.getPharmacie().getNom(), "Consultation"));

        return repository.findByUtilisateurId(id, PageRequest.of(page, Constante.NBRE_PAR_PAGE,
                Sort.by("date").descending()));
    }
}
