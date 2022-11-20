package ml.workanet.app.pharmacie.service;

import ml.workanet.app.pharmacie.domaine.Depense;
import ml.workanet.app.pharmacie.repository.DepenseRepository;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DepenseService {
    @Autowired
    private DepenseRepository repository;
    @Autowired
    private AccountService accountService;

    public Depense ajouter(Depense depense) {
        depense.setPharmacie(accountService.utilisateurActif().getPharmacie());
        depense.setUtilisateur(accountService.utilisateurActif());
        return repository.save(depense);
    }

    public Page<Depense> lister(int page) {
        return repository.findByPharmacie(accountService.utilisateurActif().getPharmacie(),
                PageRequest.of(page, Constante.NBRE_PAR_PAGE,
                Sort.by("date").descending()));
    }

    public Page<Depense> listerParDetails(int page, String details) {
        return repository.findByDetailsContainingAndPharmacie(details, accountService.utilisateurActif().getPharmacie(),
                PageRequest.of(page, Constante.NBRE_PAR_PAGE,
                Sort.by("details").ascending()));
    }


    public Depense rechercher(Long id) {
        return repository.findById(id).get();
    }

    public void supprimer(Long id) {
        repository.deleteById(id);
    }

    public List<Depense> listerDepensesMois() {
        LocalDate date = LocalDate.now();
        int annee = date.getYear();
        int mois = date.getMonth().getValue();
        int jourMax = annee % 4 == 0 ? date.getMonth().maxLength() :
                date.getMonth().minLength();
        LocalDate dateDebut = LocalDate.of(annee, mois, 1);
        LocalDate dateFin = LocalDate.of(annee, mois, jourMax);
        List<Depense> depenses = repository.findByDateBetweenAndPharmacie(dateDebut,
                dateFin,accountService.utilisateurActif().getPharmacie() );

        List<Depense> depensesSansDoublon = new ArrayList<>();
        HashMap<String, Long> depensesTypeParMontant = new HashMap<>();

        for (Depense depense : depenses) {
            String type = depense.getType();
            long montant = depense.getMontant();

            if (depensesTypeParMontant.containsKey(type)) {
                montant += depensesTypeParMontant.get(type);
            }
            depensesTypeParMontant.put(type, montant);
        }

        for (Map.Entry entry : depensesTypeParMontant.entrySet()) {
            String type = (String) entry.getKey();
            long montant = (long) entry.getValue();

            Depense depense = new Depense();
            depense.setType(type);
            depense.setMontant(montant);
            depensesSansDoublon.add(depense);
        }
        return depensesSansDoublon;

    }

    public Long sommeDepensesMois(){
        return listerDepensesMois().stream().mapToLong(Depense::getMontant).sum();
    }
}
