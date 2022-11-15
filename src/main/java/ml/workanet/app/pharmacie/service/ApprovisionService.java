package ml.workanet.app.pharmacie.service;

import ml.workanet.app.pharmacie.domaine.*;
import ml.workanet.app.pharmacie.repository.ApprovisionRepository;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApprovisionService {
    @Autowired
    private ApprovisionRepository repository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MedicamentService medicamentService;
    @Autowired
    private ES_MedicamentService es_medicamentService;
    @Autowired
    private StockService stockService;
    @Autowired
    private AuditService auditService;

    public Approvision ajouter(Approvision approvision) {
        auditService.ajouter(new Audit("Ajout approvision", "Montant "
                + approvision.getMontant() + " " + approvision.getDate() + " "
                + approvision.getHeure()));
        approvision.setUtilisateur(accountService.utilisateurActif());
        approvision.setPharmacie(accountService.utilisateurActif().getPharmacie());

        float total = 0;
        List<ES_Medicament> es_medicaments = approvision.getMedicaments();


        for (ES_Medicament es_med : es_medicaments) {
            total += es_med.getQuantite() * es_med.getPrix();
            Medicament medicament = medicamentService.rechercher(es_med.getMedicament().getId());
            es_med.setMedicament(medicament);

            Stock stock = stockService.rechercheParMed(medicament, approvision.getPharmacie());
            if (stock == null) {
                stock = new Stock();
                stock.setQuantite(es_med.getQuantite());
                stock.setPharmacie(approvision.getPharmacie());
                stock.setMedicament(medicament);
                stockService.ajouter(stock);
            } else {
                stock.setQuantite(stock.getQuantite() + es_med.getQuantite());
                stockService.ajouter(stock);
            }
        }

        es_medicaments = es_medicamentService.ajouterListe(es_medicaments);

        approvision.setMontant(total);
        approvision.setMedicaments(es_medicaments);

        return repository.save(approvision);
    }

    public Approvision rechercher(Long id) {
        Approvision approvision = repository.findById(id).get();
        auditService.ajouter(new Audit("Cherche approvision", "Montant "
                + approvision.getMontant() + " " + approvision.getDate() + " "
                + approvision.getHeure()));
        return approvision;
    }

    public void supprimer(Long id) {
        Approvision approvision = repository.findById(id).get();
        auditService.ajouter(new Audit("Suppression approvision", "Montant "
                + approvision.getMontant() + " " + approvision.getDate() + " "
                + approvision.getHeure()));
        repository.deleteById(id);
    }

    public Page<Approvision> lister(int page, int nbreParPage) {
        auditService.ajouter(new Audit("Liste Approvision", "Consultation"));
        return repository.findAll(PageRequest.of(page, nbreParPage,
                Sort.by("date").descending().and(Sort.by("heure").ascending())));
    }
}

