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
        model.addAttribute("depense", new Depense());
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "depense/nouveau";
    }

    @PostMapping(Endpoint.NOUVEAU)
    public String ajouterDepense(@ModelAttribute @Valid Depense depense, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("utilisateurActif", accountService.utilisateurActif());
            return "depense/nouveau";
        }
        depense = service.ajouter(depense);
        return "redirect:/depense";
    }

    @GetMapping(Endpoint.SUPPRESSION + Endpoint.ID)
    public String supprimerDepense(@PathVariable Long id, Model model) {
        log.info("Suppression Depense " + service.rechercher(id));
        service.supprimer(id);
        return "redirect:/depense";
    }

    @GetMapping
    public String depenseListe(@RequestParam(defaultValue = "0") int page, Model model) {
        log.info("Consultation Depense");
        Page<Depense> auditPage = service.lister(page);

        model.addAttribute("depenses", auditPage.getContent());
        model.addAttribute("totalElement", auditPage.getTotalElements());
        model.addAttribute("totalPage", new int[auditPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", auditPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());

        return "depense/liste";
    }

    @GetMapping(Endpoint.DETAILS)
    public String depenseListeParDetails(@RequestParam(defaultValue = "0") int page, @RequestParam String details, Model model) {
        log.info("Consultation Depense par details : " + details);

        Page<Depense> depensePage = service.listerParDetails(page, details);

        model.addAttribute("depenses", depensePage.getContent());
        model.addAttribute("totalElement", depensePage.getTotalElements());
        model.addAttribute("totalPage", new int[depensePage.getTotalPages()]);
        model.addAttribute("nbTotalPage", depensePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());

        return "depense/liste";
    }
}
