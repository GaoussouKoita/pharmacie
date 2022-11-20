package ml.workanet.app.pharmacie.controller;


import lombok.extern.slf4j.Slf4j;
import ml.workanet.app.pharmacie.domaine.Vente;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.service.MedicamentService;
import ml.workanet.app.pharmacie.service.VenteService;
import ml.workanet.app.pharmacie.utils.Constante;
import ml.workanet.app.pharmacie.utils.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@Slf4j
@RequestMapping(Endpoint.VENTE)
public class VenteController {

    @Autowired
    private VenteService service;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MedicamentService medicamentService;


    @GetMapping(Endpoint.NOUVEAU)
    public String formVente(Model model) {
        log.info("Formulaire Vente");
        model.addAttribute("vente", new Vente());
        model.addAttribute("medicaments", medicamentService.lister());
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "vente/nouveau";
    }

    @PostMapping(Endpoint.NOUVEAU)
    public String ajouterVente(@ModelAttribute("vente") Vente vente,
                               Errors errors, Model model) {
        log.info("Ajout de Vente : " + vente);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        vente = service.ajouter(vente);
        model.addAttribute("vente", vente);

        return "vente/info";
    }


    @GetMapping(Endpoint.SUPPRESSION + Endpoint.ID)
    public String supprimerVente(@PathVariable("id") Long id) {
        log.info("Suppression Vente : " + service.rechercher(id));
        service.supprimer(id);
        return "redirect:/vente";

    }

    @GetMapping(Endpoint.INFO + Endpoint.ID)
    public String infoVente(@PathVariable("id") Long id, Model model) {
        log.info("Info Vente : " + service.rechercher(id));
        model.addAttribute("vente", service.rechercher(id));
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "vente/info";
    }

    @GetMapping
    public String listerVentes(Model model, @RequestParam(defaultValue = "0") int page) {
        log.info("Lister Ventes");
        Page<Vente> ventePage = service.lister(page, Constante.NBRE_PAR_PAGE);
        pagination(ventePage, page, model);
        return "vente/liste";
    }

    @GetMapping(Endpoint.DETAILS)
    public String rechercherVentes(Model model, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date, @RequestParam(defaultValue = "0") int page) {
        log.info("Liste Vente par Date : " + date);
        Page<Vente> ventePage = service.listerParDate(date, page);
        model.addAttribute("date", date);
        pagination(ventePage, page, model);

        return "vente/liste";
    }

    private void pagination(Page<Vente> ventePage, int page, Model model) {

        model.addAttribute("ventes", ventePage.getContent());
        model.addAttribute("totalElement", ventePage.getTotalElements());
        model.addAttribute("totalPage", new int[ventePage.getTotalPages()]);
        model.addAttribute("nbTotalPage", ventePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());

    }
}