package ml.workanet.app.pharmacie.securite.controller;

import lombok.extern.slf4j.Slf4j;
import ml.workanet.app.pharmacie.securite.entity.ChangePassword;
import ml.workanet.app.pharmacie.securite.entity.Utilisateur;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.service.PharmacieService;
import ml.workanet.app.pharmacie.utils.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@Slf4j
@RequestMapping(Endpoint.UTILISATEUR)
public class UtilisateurController {


    @Autowired
    private AccountService service;
    @Autowired
    private PharmacieService pharmacieService;


    @GetMapping(Endpoint.NOUVEAU)
    public String addForm(Model model) {
        log.info("Formulaire user");
        model.addAttribute("pharmacies", pharmacieService.lister());
        model.addAttribute("utilisateur", new Utilisateur());
        model.addAttribute("roles", service.utilisateurActif().getRoles());
        model.addAttribute("utilisateurActif", service.utilisateurActif());
        return "utilisateur/nouveau";
    }

    @PostMapping(Endpoint.NOUVEAU)
    public String add(@ModelAttribute("utilisateur") @Valid Utilisateur utilisateur, Errors errors, Model model) {
        log.info("Ajout user dans la bd");
        model.addAttribute("utilisateurActif", service.utilisateurActif());
        if (errors.hasErrors() || !utilisateur.getPassword().equals(utilisateur.getConfirmation())) {
            model.addAttribute("roles", service.utilisateurActif().getRoles());
            model.addAttribute("pharmacies", pharmacieService.lister());

            if (!utilisateur.getPassword().equals(utilisateur.getConfirmation())) {
                errors.rejectValue("password", "", "Les deux mots de passe sont differents");
                errors.rejectValue("confirmation", "", "Les deux mots de passe sont differents");
            }
            return "utilisateur/nouveau";

        } else {
            Utilisateur utilisateur1 = service.ajouterUtilisateur(utilisateur);
            model.addAttribute("utilisateur", utilisateur1);
            return "utilisateur/info";
        }


    }

    @GetMapping(Endpoint.MODIFICATION + Endpoint.ID)
    public String modifier(@PathVariable Long id, Model model) {
        log.info("Update user");
        model.addAttribute("pharmacies", pharmacieService.lister());
        model.addAttribute("utilisateurActif", service.utilisateurActif());
        model.addAttribute("utilisateur", service.rechercher(id));
        model.addAttribute("userRoles", service.rechercher(id).getRoles());
        model.addAttribute("roles", service.utilisateurActif().getRoles());
        return "utilisateur/nouveau";
    }

    @GetMapping(Endpoint.DESACTIVER + Endpoint.ID)
    public String delete(@PathVariable Long id) {
        log.warn("Desactivation user");
        service.desactiver(id);
        return "redirect:/utilisateur";

    }

    @GetMapping(Endpoint.INFO + Endpoint.ID)
    public String rechercher(@PathVariable Long id, Model model) {
        model.addAttribute("utilisateurActif", service.utilisateurActif());
        model.addAttribute("utilisateur", service.rechercher(id));
        return "utilisateur/info";
    }

    @GetMapping(Endpoint.DETAILS)
    public String rechercherParNom(@RequestParam(defaultValue = "0") int page, @RequestParam String nom, Model model) {

        Page<Utilisateur> utilisateurPage = service.lister(nom, page);

        model.addAttribute("utilisateurs", utilisateurPage.getContent());
        model.addAttribute("totalElement", utilisateurPage.getTotalElements());
        model.addAttribute("totalPage", new int[utilisateurPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", utilisateurPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("utilisateurActif", service.utilisateurActif());
        return "utilisateur/liste";
    }

    @GetMapping
    public String all(Model model, @RequestParam(defaultValue = "0") int page) {
        log.info("Lister users");
        Page<Utilisateur> utilisateurPage = service.lister(page);

        model.addAttribute("utilisateurs", utilisateurPage.getContent());
        model.addAttribute("totalElement", utilisateurPage.getTotalElements());
        model.addAttribute("totalPage", new int[utilisateurPage.getTotalPages()]);
        model.addAttribute("nbTotalPage", utilisateurPage.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("utilisateurActif", service.utilisateurActif());
        model.addAttribute("pharmacie", service.utilisateurActif().getPharmacie());

        AtomicBoolean isRoot = new AtomicBoolean(false);

        service.utilisateurActif().getRoles().forEach(role -> {
            if (role.getRoleName().equals("ROOT")) {
                isRoot.set(true);
            }
        });
        if (isRoot.get()) {
            return "utilisateur/listeRoot";
        }
        return "utilisateur/liste";

    }


    @GetMapping(Endpoint.PASSWORD)
    public String changePasswordForm(Model model) {
        log.info("Formulaire Changement password");
         model.addAttribute("changePassword", new ChangePassword());
        model.addAttribute("utilisateurActif", service.utilisateurActif());
        return "utilisateur/password";
    }

    @PostMapping(Endpoint.PASSWORD)
    public String PasswordForm(@ModelAttribute("changePassword") @Valid ChangePassword changePassword,
                               Errors errors, Model model) throws ServletException {
        log.info("Changement de Password");
        model.addAttribute("utilisateurActif", service.utilisateurActif());

        if (service.verifierPassword(changePassword) == 0) {
            service.verifierPassword(changePassword);
            return "redirect:/utilisateur";
        } else if (service.verifierPassword(changePassword) == 1) {
            errors.rejectValue("newPassword", "",
                    "Le password et la confirmation sont different");
        } else {
            errors.rejectValue("oldPassword", "",
                    "L'ancien password est incorrect");
        }
        return "utilisateur/password";
    }

}
