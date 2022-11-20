package ml.workanet.app.pharmacie.service;

import ml.workanet.app.pharmacie.domaine.ES_Medicament;
import ml.workanet.app.pharmacie.repository.ES_MedicamentRepository;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ES_MedicamentService {
    @Autowired
    private ES_MedicamentRepository repository;
    @Autowired
    private AccountService accountService;

    public ES_Medicament ajouter(ES_Medicament medicament) {

        return repository.save(medicament);
    }

    public List<ES_Medicament> ajouterListe(List<ES_Medicament> medicaments) {
        return repository.saveAll(medicaments);
    }

    public ES_Medicament rechercher(Long id) {
        return repository.findById(id).get();
    }

    public void supprimer(Long id) {
        repository.deleteById(id);
    }

    public Page<ES_Medicament> lister(int page, int nbreParPage) {
        return repository.findAll(PageRequest.of(page, nbreParPage,
                Sort.by("date").descending().and(Sort.by("heure").ascending())));
    }
}
