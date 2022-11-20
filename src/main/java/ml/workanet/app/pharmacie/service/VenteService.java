package ml.workanet.app.pharmacie.service;

import ml.workanet.app.pharmacie.domaine.*;
import ml.workanet.app.pharmacie.repository.VenteRepository;
import ml.workanet.app.pharmacie.securite.entity.Utilisateur;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private ES_MedicamentService es_medService;


    public Vente ajouter(Vente vente) {
        auditService.ajouter(new Audit("Ajout vente", "Montant "
                + vente.getMontant() + " " + vente.getDate() + " "
                + vente.getHeure()));
        vente.setUtilisateur(accountService.utilisateurActif());
        vente.setPharmacie(accountService.utilisateurActif().getPharmacie());
        long total = 0;
        int quantite = 0;

        List<ES_Medicament> es_medicaments = vente.getMedicaments();
        es_medicaments = es_medService.ajouterListe(es_medicaments);


        for (ES_Medicament es_med : es_medicaments) {
            Medicament medicament = medService.listerParNom(es_med.getMedicament().getId());
            es_med.setMedicament(medicament);
            es_med.setPrix(medicament.getPrixVente());

            total += medicament.getPrixVente() * es_med.getQuantite();

            quantite += es_med.getQuantite();
            Stock stock = stockService.rechercheParMed(medicament, vente.getPharmacie());
            if (stock == null) {
                stock = new Stock();
                stock.setQuantite(es_med.getQuantite());
                stock.setPharmacie(vente.getPharmacie());
                stock.setMedicament(es_med.getMedicament());
                stockService.ajouter(stock);
            } else {

                if (stockService.isStockSup(es_med, stock)) {
                    stock.setQuantite(stock.getQuantite() - es_med.getQuantite());
                    stockService.ajouter(stock);
                }
            }

        }

        vente.setMontant(total);
        vente.setNbreMedicament(quantite);

        return repository.save(vente);
    }

    public Vente rechercher(Long id) {
        Vente vente = repository.findById(id).get();
        auditService.ajouter(new Audit("Recherche Vente", "Montant "
                + vente.getMontant() + " " + vente.getDate() + " "
                + vente.getHeure()));
        return repository.findById(id).get();
    }

    public void supprimer(Long id) {
        Vente vente = repository.findById(id).get();
        auditService.ajouter(new Audit("Suppression Vente", "Montant "
                + vente.getMontant() + " " + vente.getDate() + " "
                + vente.getHeure()));
        repository.deleteById(id);
    }

    public Page<Vente> lister(int page, int nbreParPage) {

        auditService.ajouter(new Audit("Liste Vente", "Consultation"));
        return repository.findByPharmacie(accountService.utilisateurActif().getPharmacie(),
                PageRequest.of(page, Constante.NBRE_PAR_PAGE,
                        Sort.by("date").descending().and(Sort.by("heure").ascending())));
    }

    public Page<Vente> listerParDate(LocalDate date, int page) {
        return repository.findByDateBetweenAndPharmacie(date, date,
                accountService.utilisateurActif().getPharmacie(),
                PageRequest.of(page, Constante.NBRE_PAR_PAGE));
    }
    public List<Vente> listerParDate(LocalDate date) {
        return repository.findByDateBetweenAndPharmacie(date, date,
                accountService.utilisateurActif().getPharmacie());
    }

    public List<Vente> listerParDates(LocalDate date1, LocalDate date2) {
        return repository.findByDateBetweenAndPharmacie(date1, date2,
                accountService.utilisateurActif().getPharmacie());
    }

    public List<Vente> ventesDuJourParUtilisateur(Utilisateur utilisateur) {
        return repository.findByDateAndUtilisateur(LocalDate.now(), utilisateur);
    }

    public List<Vente> listerParUtilisateurs() {
        return repository.findDistinctUtilisateurByDateAndPharmacie(LocalDate.now(),
                accountService.utilisateurActif().getPharmacie());
    }

    public HashMap<String, Long> ventesDuJour(List<Vente> ventes) {
        HashMap<String, Long> nbProdVenduTotalVente = new HashMap<>();
        long nbreProduitVendu = 0;
        long totalVente = 0;

        for (Vente vente : ventes) {
            for (ES_Medicament medicaments : vente.getMedicaments()) {
                nbreProduitVendu += medicaments.getQuantite();
            }
            totalVente += vente.getMontant();
        }
        nbProdVenduTotalVente.put("nbreMedVendu", nbreProduitVendu);
        nbProdVenduTotalVente.put("totalVente", totalVente);
        return nbProdVenduTotalVente;
    }


    public List<ES_Medicament> medDeLaVente(List<Vente> ventesDuJour) {
        List<ES_Medicament> medicamentsAvecDoublon = new ArrayList<>();
        List<ES_Medicament> medicamentsSansDoublon = new ArrayList<>();
        HashMap<String, Integer> medNomParQuantite = new HashMap<>();

        for (Vente vente : ventesDuJour) {
            medicamentsAvecDoublon.addAll(vente.getMedicaments());
        }

        for (ES_Medicament medicaments : medicamentsAvecDoublon) {
            String nom = medicaments.getMedicament().getNom();
            int quantite = medicaments.getQuantite();

            if (medNomParQuantite.containsKey(nom)) {
                quantite += medNomParQuantite.get(nom);
            }
            medNomParQuantite.put(nom, quantite);
        }
        for (Map.Entry entry : medNomParQuantite.entrySet()) {
            String nom = (String) entry.getKey();
            int quantite = (int) entry.getValue();

            Medicament medicament = medService.listerParNom(nom);
            ES_Medicament medicaments = new ES_Medicament();
            medicaments.setMedicament(medicament);
            medicaments.setQuantite(quantite);
            medicaments.setPrix(medicament.getPrixVente());
            medicamentsSansDoublon.add(medicaments);
        }
        return medicamentsSansDoublon;

    }


    public List<Vente> venteTotalParUtilisateur() {
        List<Vente> ventesDuJour = listerParUtilisateurs();
        List<Vente> ventesSansDoublon = new ArrayList<>();
        HashMap<Utilisateur, Long> totalVentesUtilisateur = new HashMap<>();

        for (Vente vente : ventesDuJour) {
            Utilisateur utilisateur = vente.getUtilisateur();
            long montant = vente.getMontant();

            if (totalVentesUtilisateur.containsKey(utilisateur)) {
                montant += totalVentesUtilisateur.get(utilisateur);
            }
            totalVentesUtilisateur.put(utilisateur, montant);
        }

        for (Map.Entry entry : totalVentesUtilisateur.entrySet()) {
            Utilisateur utilisateur = (Utilisateur) entry.getKey();
            long montant = (long) entry.getValue();

            Vente vente = new Vente();
            vente.setMontant(montant);
            vente.setUtilisateur(utilisateur);
            ventesSansDoublon.add(vente);
        }
        return ventesSansDoublon;

    }



    private List<Vente> ventesMois(){
        LocalDate date = LocalDate.now();
        int annee = date.getYear();
        int mois = date.getMonth().getValue();
        int jourMax = annee % 4 == 0 ? date.getMonth().maxLength() :
                date.getMonth().minLength();
        LocalDate dateDebut = LocalDate.of(annee, mois, 1);
        LocalDate dateFin = LocalDate.of(annee, mois, jourMax);
        return listerParDates(dateDebut,dateFin);
    }

    public long sommeVentesMois(){

        return  ventesMois().stream().mapToLong(Vente::getMontant).sum();
    }




}
