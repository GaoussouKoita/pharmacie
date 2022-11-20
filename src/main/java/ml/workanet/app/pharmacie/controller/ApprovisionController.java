package ml.workanet.app.pharmacie.controller;

import lombok.extern.slf4j.Slf4j;
import ml.workanet.app.pharmacie.domaine.Approvision;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.service.ApprovisionService;
import ml.workanet.app.pharmacie.service.MedicamentService;
import ml.workanet.app.pharmacie.utils.Constante;
import ml.workanet.app.pharmacie.utils.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@Controller
@Slf4j
@RequestMapping(Endpoint.APPROVISION)
public class ApprovisionController {

    @Autowired
    private ApprovisionService service;

    @Autowired
    private AccountService accountService;
    @Autowired
    private MedicamentService medicamentService;


    @GetMapping(Endpoint.NOUVEAU)
    public String formApprovision(Model model) {
        log.info("Formulaire Approvision");
        model.addAttribute("approvision", new Approvision());
        model.addAttribute("medicaments", medicamentService.lister());
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());

        return "approvision/nouveau";

    }

    @PostMapping(Endpoint.NOUVEAU)
    public String ajouterApprovision(@ModelAttribute("approvision") @Valid Approvision approvision, Errors errors, Model model) {

        log.info("Ajout Approvision : " + approvision);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        approvision = service.ajouter(approvision);
        model.addAttribute("approvision", service.rechercher(approvision.getId()));

        return "approvision/info";
    }


    @GetMapping(Endpoint.SUPPRESSION + Endpoint.ID)
    public String supprimerApprovision(@PathVariable("id") Long id) {
        log.info("Suppression Approvision : " + service.rechercher(id));
        service.supprimer(id);
        return "redirect:/approvision";

    }

    @GetMapping(Endpoint.INFO + Endpoint.ID)
    public String infoApprovision(@PathVariable("id") Long id, Model model) {
        log.info("Info Approvision : " + service.rechercher(id));
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        model.addAttribute("approvision", service.rechercher(id));
        return "approvision/info";
    }

    @GetMapping(Endpoint.DETAILS)
    public String rechercherApprovisionEntreDates(@RequestParam
       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateDebut,
       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFin,
       @RequestParam(defaultValue = "0") int page, Model model) {
        log.info("Lister Approvisions entre Dates : " + dateDebut+"-"+dateFin);
        Page<Approvision> approvisionPage = service.listerEntreDates( dateDebut, dateFin, page,Constante.NBRE_PAR_PAGE);

        pagination(approvisionPage,page, model);

        return "approvision/liste";
    }

    @GetMapping
    public String listerApprovision(Model model, @RequestParam(defaultValue = "0") int page) {
        log.info("Listes Approvisions");

        Page<Approvision> approvisionPage = service.lister(page, Constante.NBRE_PAR_PAGE);
        pagination(approvisionPage, page, model);
        return "approvision/liste";
    }


    private void pagination(Page<Approvision> approvisionPage, int page, Model model) {

        model.addAttribute("approvisions", approvisionPage.getContent());
        model.addAttribute("totalElement", approvisionPage.getTotalElements());
        model.addAttribute("totalPage", new int[approvisionPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", approvisionPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());

    }
}
