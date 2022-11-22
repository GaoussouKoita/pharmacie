package ml.workanet.app.pharmacie.service;

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
    private AccountService accountService;

    public Type ajouter(Type type) {
        type.setPharmacie(accountService.utilisateurActif().getPharmacie());
        return repository.save(type);
    }

    public Type rechercher(Long id) {
        Type type= repository.findById(id).get();
        return repository.findById(id).get();
    }

    public void supprimer(Long id) {
        Type type= repository.findById(id).get();
        repository.deleteById(id);
    }

    public Page lister(int page, int nbreParPage) {

        return repository.findByPharmacie(accountService.utilisateurActif().getPharmacie(),
                PageRequest.of(page, nbreParPage,
                Sort.by("nom").ascending()));
    }

    public List<Type> lister() {
        return repository.findByPharmacie(accountService.utilisateurActif().getPharmacie(),
                Sort.by("nom").ascending());
    }

}
