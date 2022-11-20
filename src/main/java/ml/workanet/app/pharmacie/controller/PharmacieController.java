package ml.workanet.app.pharmacie.controller;

import lombok.extern.slf4j.Slf4j;
import ml.workanet.app.pharmacie.domaine.Pharmacie;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.service.PharmacieService;
import ml.workanet.app.pharmacie.utils.Constante;
import ml.workanet.app.pharmacie.utils.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping(Endpoint.PHARMACIE)
public class PharmacieController {

    @Autowired
    private PharmacieService service;
    @Autowired
    private AccountService accountService;

    @GetMapping(Endpoint.NOUVEAU)
    public String formPharmacie(Model model) {
        log.info("Formulaire pharmacie ");
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        model.addAttribute("pharmacie", new Pharmacie());
        return "pharmacie/nouveau";
    }

    @PostMapping(Endpoint.NOUVEAU)
    public String ajoutPharmacie(@ModelAttribute Pharmacie pharmacie, Model model) {
        log.info("Ajout Pharmacie : " + pharmacie);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        model.addAttribute("pharmacie", service.ajouter(pharmacie));
        return "pharmacie/info";
    }

    @GetMapping(Endpoint.MODIFICATION + Endpoint.ID)
    public String modificationPharmacie(@PathVariable Long id, Model model) {
        log.info("Formulaire modification Pharmacie : " + service.rechercher(id));
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        model.addAttribute("pharmacie", service.rechercher(id));
        return "pharmacie/nouveau";
    }

    @GetMapping(Endpoint.SUPPRESSION + Endpoint.ID)
    public String suppressionPharmacie(@PathVariable Long id) {
        service.supprimer(id);
        log.info("Suppression Pharmacie : " + service.rechercher(id));
        return "redirect:/pharmacie";
    }

    @GetMapping
    public String listePharmacie(@RequestParam(defaultValue = "0") int page, Model model) {
        log.info(" Liste Pharmacie");
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        model.addAttribute("pharmacies", service.lister(page, Constante.NBRE_PAR_PAGE));
        return "pharmacie/liste";
    }

    @GetMapping(Endpoint.INFO + Endpoint.ID)
    public String infoPharmacie(@PathVariable Long id, Model model) {
        log.info("Info Pharmacie : " + service.rechercher(id));
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        model.addAttribute("pharmacie", service.rechercher(id));
        return "pharmacie/info";
    }


}
