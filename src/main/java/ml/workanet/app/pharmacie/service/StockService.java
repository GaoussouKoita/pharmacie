package ml.workanet.app.pharmacie.service;


import ml.workanet.app.pharmacie.domaine.*;
import ml.workanet.app.pharmacie.repository.StockRepository;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class StockService {
    @Autowired
    private StockRepository repository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AuditService auditService;

    public Stock ajouter(Stock stock) {
        auditService.ajouter(new Audit("Ajout Stock", stock.getMedicament().getNom()+" : "+stock.getQuantite()));

        Stock stockEncours = rechercheParMed(stock.getMedicament(), stock.getPharmacie());
        return repository.save(stock);

    }

    public Stock rechercher(Long id) {
        Stock stock= repository.findById(id).get();
        auditService.ajouter(new Audit("Recherche Stock", stock.getMedicament().getNom()+" : "+stock.getQuantite()));

        return this.repository.findById(id).get();
    }

    public void suppression(Long id) {
        Stock stock= repository.findById(id).get();
        auditService.ajouter(new Audit("Suppresion Stock",
                stock.getMedicament().getNom()+" : "+stock.getQuantite()));

        repository.deleteById(id);
    }

    public Stock rechercheParMed(Medicament medicament, Pharmacie pharmacie) {
        return repository.findByMedicamentAndPharmacie(medicament, pharmacie);
    }

    public List<Stock> liste() {
        return repository.findByPharmacie(accountService.utilisateurActif().getPharmacie(),
                Sort.by("quantite"));
    }

    public Page<Stock> liste(int page) {
        auditService.ajouter(new Audit("Liste Stock", "Consultation"));
        return repository.findByPharmacie(accountService.utilisateurActif().getPharmacie() ,
                PageRequest.of(page, Constante.NBRE_PAR_PAGE));
    }

    public Page<Stock> listerProdNom(String nom, int page) {
        auditService.ajouter(new Audit("Liste Stock par nom : "+nom, "Consultation"));

        return repository.findByMedicamentNomContainingAndPharmacie(nom,
                accountService.utilisateurActif().getPharmacie(),
                PageRequest.of(page, Constante.NBRE_PAR_PAGE));
    }

    public List<Stock> ruptureStock() {
        List<Stock> stockProdRupture = new ArrayList<>();

        for (Stock stock : repository.findByPharmacie(accountService.utilisateurActif().getPharmacie(),
                Sort.by("quantite").ascending())) {
            int stockQte = stock.getQuantite();
            Long accPhId = accountService.utilisateurActif().getPharmacie().getId();
            Long stockPhId =  stock.getPharmacie().getId();

            if ( stockQte < Constante.NBRE_STOCK_RUPTURE
            && accPhId==stockPhId) {
                stockProdRupture.add(stock);
            }
        }
        return stockProdRupture;
    }

    public boolean isStockSup(ES_Medicament es_medicament, Stock stock) {
        if (es_medicament.getQuantite() <= stock.getQuantite()) {
            return true;
        }
        return false;
    }
}
