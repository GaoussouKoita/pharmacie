package ml.workanet.app.pharmacie.service;

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
    private TypeService typeService;

    public Medicament ajouter(Medicament medicament) {
        if(medicament.isPrisEnCharge() && medicament.getPrixAssureur()==0){
            medicament.setPrixAssureur(medicament.getPrixVente());
        }

        medicament.setType(typeService.rechercher(medicament.getType().getId()));
        medicament.setPharmacie(accountService.utilisateurActif().getPharmacie());

        return repository.save(medicament);
    }

    public Medicament listerParNom(Long id) {
        Medicament medicament = repository.findById(id).get();
        return medicament;
    }

    public Medicament listerParNom(String nom) {
        Medicament medicament = repository.findByNomAndPharmacie(nom,
                accountService.utilisateurActif().getPharmacie());

        return medicament;
    }

    public void supprimer(Long id) {
        Medicament medicament = repository.findById(id).get();
        repository.deleteById(id);

    }

    public Page<Medicament> lister(int page, int nbreParPage) {
        return repository.findByPharmacie(accountService.utilisateurActif().getPharmacie(),
                PageRequest.of(page, nbreParPage, Sort.by("nom").ascending()));
    }

    public Page<Medicament> listerParNom(String nom, int page, int nbreParPage) {
        return repository.findByNomContainingAndPharmacie(nom, accountService.utilisateurActif().getPharmacie(),
                PageRequest.of(page, nbreParPage, Sort.by("nom").ascending()));
    }

    public List<Medicament> lister() {
        return repository.findByPharmacie(accountService.utilisateurActif().getPharmacie(),
                Sort.by("nom").ascending());
    }
}
