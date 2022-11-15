package ml.workanet.app.pharmacie.controller;


import lombok.extern.slf4j.Slf4j;
import ml.workanet.app.pharmacie.domaine.Type;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.service.TypeService;
import ml.workanet.app.pharmacie.utils.Constante;
import ml.workanet.app.pharmacie.utils.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping(Endpoint.TYPE)
public class TypeController {


    @Autowired
    private TypeService service;
    @Autowired
    private AccountService accountService;


    @GetMapping(Endpoint.NOUVEAU)
    public String formType(Model model) {
        log.info("Formulaire Type");
        model.addAttribute("type", new Type());
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "type/nouveau";
    }

    @PostMapping(Endpoint.NOUVEAU)
    public String ajouterType(@ModelAttribute Type type, Model model) {
        log.info("Ajout Type : "+type);
        service.ajouter(type);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "redirect:/type";
    }

    @GetMapping(Endpoint.MODIFICATION + Endpoint.ID)
    public String modifierType(@PathVariable("id") Long id, @RequestParam(defaultValue = "0") int page, Model model) {
        log.info("Formulaire Modification Type : "+service.rechercher(id));
        Type type = service.rechercher(id);
        model.addAttribute("type", type);
        pagination(page, model);
        return "type/liste";
    }

    @GetMapping(Endpoint.SUPPRESSION + Endpoint.ID)
    public String supprimerType(@PathVariable("id") Long id) {
        log.info("Suppression Type : "+service.rechercher(id));
        service.supprimer(id);
        return "redirect:/type";
    }


    @GetMapping
    public String listerType(Model model, @RequestParam(defaultValue = "0") int page) {
        log.info("Liste types");
        model.addAttribute("type", new Type());
        pagination(page, model);
        return "type/liste";
    }


    private void pagination(int page, Model model) {
        Page<Type> typePage = service.lister(page, Constante.NBRE_PAR_PAGE);
        model.addAttribute("types", typePage.getContent());
        model.addAttribute("totalElement", typePage.getTotalElements());
        model.addAttribute("totalPage", new int[typePage.getTotalPages()]);
        model.addAttribute("nbTotalPage", typePage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
    }
}