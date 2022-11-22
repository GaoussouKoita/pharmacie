package ml.workanet.app.pharmacie.controller;

import lombok.extern.slf4j.Slf4j;
import ml.workanet.app.pharmacie.domaine.Depense;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.service.DepenseService;
import ml.workanet.app.pharmacie.utils.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping(Endpoint.DEPENSE)
public class DepenseController {

    @Autowired
    private DepenseService service;
    @Autowired
    private AccountService accountService;


    @GetMapping(Endpoint.NOUVEAU)
    public String formDepense(Model model) {
        log.info("Formulaire Depense ");
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        model.addAttribute("depense", new Depense());
        return "depense/nouveau";
    }

    @PostMapping(Endpoint.NOUVEAU)
    public String ajouterDepense(@ModelAttribute @Valid Depense depense, Errors errors, Model model) {
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        if (errors.hasErrors()) {
            return "depense/nouveau";
        }
        depense = service.ajouter(depense);
        model.addAttribute("depense", depense);

        return "depense/info";
    }

    @GetMapping(Endpoint.SUPPRESSION + Endpoint.ID)
    public String supprimerDepense(@PathVariable Long id, Model model) {
        log.info("Suppression Depense " + service.rechercher(id));
        service.supprimer(id);
        return "redirect:/depense";
    }

    @GetMapping(Endpoint.INFO + Endpoint.ID)
    public String InfoDepense(@PathVariable Long id, Model model) {
        Depense depense = service.rechercher(id);
        log.info("Info Depense " + depense);
        model.addAttribute("depense", depense);
        return "depense/info";
    }

    @GetMapping
    public String depenseListe(@RequestParam(defaultValue = "0") int page, Model model) {
        log.info("Consultation Depense");
        Page<Depense> depensePage = service.lister(page);
        pagination(depensePage, page, model);
        return "depense/liste";
    }

    @GetMapping(Endpoint.DETAILS)
    public String depenseListeParDetails(@RequestParam(defaultValue = "0") int page, @RequestParam String details, Model model) {
        log.info("Consultation Depense par details : " + details);

        Page<Depense> depensePage = service.listerParDetails(page, details);
        model.addAttribute("details", details);
        pagination(depensePage, page, model);
        return "depense/liste";
    }

    private void pagination(Page<Depense> depensePage, int page, Model model) {
        model.addAttribute("depenses", depensePage.getContent());
        model.addAttribute("totalElement", depensePage.getTotalElements());
        model.addAttribute("totalPage", new int[depensePage.getTotalPages()]);
        model.addAttribute("nbTotalPage", depensePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
    }
}
