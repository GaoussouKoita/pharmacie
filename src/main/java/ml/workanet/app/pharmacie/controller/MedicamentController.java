package ml.workanet.app.pharmacie.controller;

import lombok.extern.slf4j.Slf4j;
import ml.workanet.app.pharmacie.domaine.Medicament;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.service.MedicamentService;
import ml.workanet.app.pharmacie.service.TypeService;
import ml.workanet.app.pharmacie.utils.Constante;
import ml.workanet.app.pharmacie.utils.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@Slf4j
@RequestMapping(Endpoint.MEDICAMENT)
public class MedicamentController {

    @Autowired
    private MedicamentService service;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TypeService typeService;

    @GetMapping(Endpoint.NOUVEAU)
    public String formMedicament(Model model) {
        log.info("Formulaire Medicament");
        model.addAttribute("medicament", new Medicament());
        model.addAttribute("types", typeService.lister());
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "medicament/nouveau";
    }


    @PostMapping(Endpoint.NOUVEAU)
    public String ajouterMedicament(@ModelAttribute @Valid Medicament medicament, Model model) {
        log.info("Ajout Medicament : " + medicament);
        model.addAttribute("medicament", service.ajouter(medicament));
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "medicament/info";
    }

    @GetMapping(Endpoint.MODIFICATION + Endpoint.ID)
    public String modifierMedicament(@PathVariable Long id, Model model) {
        log.info("Formulaire modification Medicament : " + service.listerParNom(id));
        model.addAttribute("medicament", service.listerParNom(id));
        model.addAttribute("types", typeService.lister());
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "medicament/nouveau";
    }

    @GetMapping(Endpoint.SUPPRESSION + Endpoint.ID)
    public String supprimerMedicament(@PathVariable Long id, Model model) {
        log.info("Suppression Medicament : " + service.listerParNom(id));
        service.supprimer(id);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "redirect:/medicament";
    }

    @GetMapping
    public String listeMedicament(@RequestParam(defaultValue = "0") Integer page, Model model) {
        log.info("Liste Medicament");
        Page<Medicament> medicamentPage = service.lister(page, Constante.NBRE_PAR_PAGE);

        pagination(medicamentPage, page, model);
        return "medicament/liste";
    }

    @GetMapping(Endpoint.INFO + Endpoint.ID)
    public String infoMedicament(@PathVariable Long id, Model model) {
        log.info("Info Medicament : " + service.listerParNom(id));
        model.addAttribute("medicament", service.listerParNom(id));
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "medicament/info";
    }

    @GetMapping(Endpoint.DETAILS)
    public String detailsMedicament(@RequestParam String nom, @RequestParam(defaultValue = "0") int page, Model model) {
        log.info("Recherche Medicament par nom : " + nom);
        Page<Medicament> medicamentPage = service.listerParNom(nom, page, Constante.NBRE_PAR_PAGE);

        pagination(medicamentPage, page, model);
        return "medicament/liste";
    }


    private void pagination(Page<Medicament> medicamentPage, int page, Model model) {

        model.addAttribute("medicaments", medicamentPage.getContent());
        model.addAttribute("totalElement", medicamentPage.getTotalElements());
        model.addAttribute("totalPage", new int[medicamentPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", medicamentPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
    }

}
