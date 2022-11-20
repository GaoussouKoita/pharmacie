package ml.workanet.app.pharmacie.utils;


import lombok.extern.slf4j.Slf4j;
import ml.workanet.app.pharmacie.domaine.ES_Medicament;
import ml.workanet.app.pharmacie.domaine.Stock;
import ml.workanet.app.pharmacie.domaine.Vente;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.service.StockService;
import ml.workanet.app.pharmacie.service.VenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private VenteService venteService;
    @Autowired
    private StockService stockService;


    @GetMapping(Endpoint.ACCEUIL)
    public String home(Model model) {

        log.info("Page d'acceuil");

        List<Vente> ventesDuJour = venteService.ventesDuJourParUtilisateur(accountService.utilisateurActif());
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

        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "acceuil/index";
    }
}