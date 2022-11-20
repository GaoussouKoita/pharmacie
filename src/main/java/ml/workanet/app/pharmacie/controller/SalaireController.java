package ml.workanet.app.pharmacie.controller;

import lombok.extern.slf4j.Slf4j;
import ml.workanet.app.pharmacie.domaine.Salaire;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.service.SalaireService;
import ml.workanet.app.pharmacie.service.TypeService;
import ml.workanet.app.pharmacie.utils.Constante;
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
@RequestMapping(Endpoint.SALAIRE)
public class SalaireController {

    @Autowired
    private SalaireService service;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TypeService typeService;

    @GetMapping(Endpoint.NOUVEAU)
    public String formSalaire(Model model) {
        log.info("Formulaire Salaire");
        model.addAttribute("salaire", new Salaire());
        model.addAttribute("utilisateurs", accountService.lister());
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "salaire/nouveau";
    }


    @PostMapping(Endpoint.NOUVEAU)
    public String ajouterSalaire(@ModelAttribute @Valid Salaire salaire, Errors errors, Model model) {
        log.info("Ajout Salaire : " + salaire);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        if(errors.hasErrors()){
            model.addAttribute("utilisateurs", accountService.lister());
            return "salaire/nouveau";
        }
        model.addAttribute("salaire", service.ajouter(salaire));
        return "salaire/info";
    }

    @GetMapping(Endpoint.SUPPRESSION + Endpoint.ID)
    public String supprimerSalaire(@PathVariable Long id, Model model) {
        log.info("Suppression Salaire : " + service.rechercher(id));
        service.supprimer(id);
        return "redirect:/salaire";
    }

    @GetMapping
    public String listeSalaire(@RequestParam(defaultValue = "0") Integer page, Model model) {
        log.info("Liste Salaire");
        Page<Salaire> salairePage = service.lister(page, Constante.NBRE_PAR_PAGE);

        pagination(salairePage, page, model);
        return "salaire/liste";
    }

    @GetMapping(Endpoint.INFO + Endpoint.ID)
    public String infoSalaire(@PathVariable Long id, Model model) {
        log.info("Info Salaire : " + service.rechercher(id));
        model.addAttribute("salaire", service.rechercher(id));
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "salaire/info";
    }

    @GetMapping(Endpoint.DETAILS)
    public String detailsSalaire(@RequestParam String nom, @RequestParam(defaultValue = "0") int page, Model model) {
        log.info("Recherche Salaire par nom d'utilisateur : " + nom);
        Page<Salaire> salairePage = service.rechercher(nom, page, Constante.NBRE_PAR_PAGE);

        pagination(salairePage, page, model);
        return "salaire/liste";
    }


    private void pagination(Page<Salaire> salairePage, int page, Model model) {

        model.addAttribute("salaires", salairePage.getContent());
        model.addAttribute("totalElement", salairePage.getTotalElements());
        model.addAttribute("totalPage", new int[salairePage.getTotalPages()]);
        model.addAttribute("nbTotalPage", salairePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
    }

}
