package ml.workanet.app.pharmacie.service;


import ml.workanet.app.pharmacie.domaine.*;
import ml.workanet.app.pharmacie.repository.StockRepository;
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
        auditService.ajouter(new Audit("Suppresion Stock", stock.getMedicament().getNom()+" : "+stock.getQuantite()));

        repository.deleteById(id);
    }

    public Stock rechercheParMed(Medicament medicament, Pharmacie pharmacie) {
        return repository.findByMedicamentAndPharmacie(medicament, pharmacie);
    }

    public List<Stock> liste() {
        return repository.findAll();
    }

    public Page<Stock> liste(int page) {
        auditService.ajouter(new Audit("Liste Stock", "Consultation"));

        return repository.findAll(PageRequest.of(page, Constante.NBRE_PAR_PAGE));
    }

    public Page<Stock> listeProdNom(String nom, int page) {
        auditService.ajouter(new Audit("Liste Stock par nom : "+nom, "Consultation"));

        return repository.findByMedicamentNomContaining(nom, PageRequest.of(page, Constante.NBRE_PAR_PAGE));
    }

    public List<Stock> ruptureStock() {
        List<Stock> stockProdRupture = new ArrayList<>();

        for (Stock stock : repository.findAll(Sort.by("quantite").ascending())) {
            if (stock.getQuantite() < Constante.NBRE_STOCK_RUPTURE) {
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
