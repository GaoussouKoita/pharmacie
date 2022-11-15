package ml.workanet.app.pharmacie.service;

import ml.workanet.app.pharmacie.domaine.Audit;
import ml.workanet.app.pharmacie.domaine.Type;
import ml.workanet.app.pharmacie.repository.TypeRepository;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeService {
    @Autowired
    private TypeRepository repository;
    @Autowired
    private AuditService auditService;
    @Autowired
    private AccountService accountService;

    public Type ajouter(Type type) {
        auditService.ajouter(new Audit("Ajout Type", type.getNom()));
        type.setPharmacie(accountService.utilisateurActif().getPharmacie());
        return repository.save(type);
    }

    public Type rechercher(Long id) {
        Type type= repository.findById(id).get();
        auditService.ajouter(new Audit("Recherche Type", type.getNom()));

        return repository.findById(id).get();
    }

    public void supprimer(Long id) {
        Type type= repository.findById(id).get();
        auditService.ajouter(new Audit("Recherche Type", type.getNom()));
        repository.deleteById(id);
    }

    public Page lister(int page, int nbreParPage) {

        return repository.findAll(PageRequest.of(page, nbreParPage,
                Sort.by("nom").ascending()));
    }

    public List<Type> lister() {
        auditService.ajouter(new Audit("Liste Type", "Consultation"));
        return repository.findAll(
                Sort.by("nom").ascending());
    }

}
