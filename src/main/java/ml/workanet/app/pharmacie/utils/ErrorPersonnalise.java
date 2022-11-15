package ml.workanet.app.pharmacie.utils;


import ml.workanet.app.pharmacie.securite.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Classe des erreurs personnalisees
 * Ici on passe les donnees du modele a la vue comme les infos
 * de l'user connecte.
 * Sinon on pouvait  juste utilise les status code comme nom
 * de vue des differentes erreurs dans le dossier error
 */


@Controller
public class ErrorPersonnalise implements ErrorController {

    @Autowired
    private AccountService accountService;


    @GetMapping(Endpoint.ERROR)
    public String notFound(HttpServletRequest request, Model model) {

        String errorPage = "erreur/";
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        model.addAttribute("status", status);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorPage += "403";
            } else if (statusCode == HttpStatus.NOT_FOUND.value()) {

                errorPage += "404";

            } else if (statusCode == HttpStatus.METHOD_NOT_ALLOWED.value()) {
                errorPage += "erreur";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorPage += "500";
            } else {
                errorPage += "erreur";
            }
        }
        return errorPage;
    }

}
