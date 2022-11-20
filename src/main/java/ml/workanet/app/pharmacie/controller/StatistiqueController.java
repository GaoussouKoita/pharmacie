package ml.workanet.app.pharmacie.controller;

import lombok.extern.slf4j.Slf4j;
import ml.workanet.app.pharmacie.domaine.ES_Medicament;
import ml.workanet.app.pharmacie.domaine.Stock;
import ml.workanet.app.pharmacie.domaine.Vente;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.service.DepenseService;
import ml.workanet.app.pharmacie.service.StockService;
import ml.workanet.app.pharmacie.service.VenteService;
import ml.workanet.app.pharmacie.utils.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
@Slf4j
@RequestMapping(Endpoint.STATISTIQUE)
public class StatistiqueController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private VenteService venteService;
    @Autowired
    private StockService stockService;
    @Autowired
    private DepenseService depenseService;

    @GetMapping
    public String statistiqueDuJour(Model model){

        List<Vente> ventesDuJour = venteService.listerParDate(LocalDate.now());
        List<ES_Medicament> medicaments = venteService.medDeLaVente(ventesDuJour);

        List<Stock> stockProdRupture = stockService.ruptureStock();



        int nbreMedVendu = Integer.parseInt(String.valueOf(
                venteService.ventesDuJour(ventesDuJour).get("nbreMedVendu")));
        double totalVente = Double.parseDouble(
                String.valueOf(venteService.ventesDuJour(ventesDuJour)
                        .get("totalVente")));


        model.addAttribute("stocks", stockProdRupture);
        model.addAttribute("nbreMedVendu", nbreMedVendu);
        model.addAttribute("totalVente", totalVente);
        model.addAttribute("medicaments", medicaments);

        model.addAttribute("sommeVentesMois", venteService.sommeVentesMois());
        model.addAttribute("sommeDepensesMois", depenseService.sommeDepensesMois());
        model.addAttribute("depensesMois", depenseService.listerDepensesMois());
        model.addAttribute("ventesTotalParUtilisateur", venteService.venteTotalParUtilisateur());
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "statistique/dashboard";
    }


    @GetMapping(Endpoint.DETAILS)
    public String statistiqueDates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                       LocalDate dateDebut,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                       LocalDate dateFin, Model model) {

        List<Vente> ventesDates = venteService.listerParDates(dateDebut, dateFin);
        List<ES_Medicament> medicaments = venteService.medDeLaVente(ventesDates);

        int nbMedDates = Integer.parseInt(
                String.valueOf(venteService.ventesDuJour(ventesDates)
                        .get("nbreMedVendu")));

        double totalVenteDates = venteService.
                ventesDuJour(ventesDates).get("totalVente");

        model.addAttribute("utilisateuActifr", accountService.utilisateurActif());
        model.addAttribute("nbMedDates", nbMedDates);
        model.addAttribute("totalVenteDates", totalVenteDates);
        model.addAttribute("medVenduDates", medicaments);
        model.addAttribute("dateDebut", dateDebut);
        model.addAttribute("dateFin", dateFin);

        statistiqueDuJour(model);

        return "statistique/dashboard";
    }



    @GetMapping(Endpoint.CHART_VENTE_UTILISATEUR)
    @ResponseBody
    public List<ES_Medicament> venteUtilisateurChart() {
        return venteService.medDeLaVente(venteService.
                ventesDuJourParUtilisateur(accountService.utilisateurActif()));
    }

    @GetMapping(Endpoint.CHART_VENTE)
    @ResponseBody
    public List<ES_Medicament> venteChart() {
        return venteService.medDeLaVente(venteService.listerParDate(LocalDate.now()));
    }


    @GetMapping(Endpoint.CHART_VENTE_DATES)
    @ResponseBody
    public List<ES_Medicament> ventesDatesChart(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                        LocalDate dateDebut,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                        LocalDate dateFin) {
        return venteService.medDeLaVente(venteService.listerParDates(dateDebut, dateFin));

    }
    @GetMapping(Endpoint.CHART_VENTE_UTILISATEURS)
    @ResponseBody
    public List<Vente> venteUtilisataeursChart() {
        return venteService.venteTotalParUtilisateur();
    }

}
