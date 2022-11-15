package ml.workanet.app.pharmacie.service;

import ml.workanet.app.pharmacie.domaine.*;
import ml.workanet.app.pharmacie.repository.VenteRepository;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VenteService {

    @Autowired
    private VenteRepository repository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MedicamentService medService;
    @Autowired
    private StockService stockService;
@Autowired
private AuditService auditService;
    public Vente ajouter(Vente vente) {
        auditService.ajouter(new Audit("Ajout vente", "Montant "
                + vente.getMontant() + " " + vente.getDate() + " "
                + vente.getHeure()));
        vente.setUtilisateur(accountService.utilisateurActif());
        vente.setPharmacie(accountService.utilisateurActif().getPharmacie());
        float total = 0;

        List<ES_Medicament> es_medicaments = vente.getMedicaments();

        for (ES_Medicament es_med : es_medicaments) {
            Medicament medicament =medService.rechercher(es_med.getMedicament().getId());
            es_med.setMedicament(medicament);

            Stock stock = stockService.rechercheParMed(medicament, vente.getPharmacie());
            if (stock == null) {
                stock = new Stock();
                stock.setQuantite(es_med.getQuantite());
                stock.setPharmacie(vente.getPharmacie());
                stock.setMedicament(es_med.getMedicament());
                stockService.ajouter(stock);
            } else {

                if (stockService.isStockSup(es_med, stock)) {
                    stock.setQuantite(stock.getQuantite()-es_med.getQuantite());
                    stockService.ajouter(stock);
                }
            }
            total +=medicament.getPrixVente();
        }
        vente.setMontant(total);

        return repository.save(vente);
    }

    public Vente rechercher(Long id) {
        Vente vente= repository.findById(id).get();
        auditService.ajouter(new Audit("Recherche Vente", "Montant "
                + vente.getMontant() + " " + vente.getDate() + " "
                + vente.getHeure()));

        return repository.findById(id).get();
    }

    public void supprimer(Long id) {
        Vente vente= repository.findById(id).get();
        auditService.ajouter(new Audit("Suppression Vente", "Montant "
                + vente.getMontant() + " " + vente.getDate() + " "
                + vente.getHeure()));
        repository.deleteById(id);
    }

    public Page<Vente> lister(int page, int nbreParPage) {
        auditService.ajouter(new Audit("Liste Vente", "Consultation"));
        return repository.findAll(PageRequest.of(page, nbreParPage,
                Sort.by("date").descending().and(Sort.by("heure").ascending())));
    }
}
