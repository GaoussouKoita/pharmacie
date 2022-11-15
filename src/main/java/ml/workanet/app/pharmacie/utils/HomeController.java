package ml.workanet.app.pharmacie.utils;


import lombok.extern.slf4j.Slf4j;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class HomeController {

    @Autowired
    private AccountService accountService;


    @GetMapping(Endpoint.ACCEUIL)
    public String home(Model model) {

        log.info("Page d'acceuil");
        model.addAttribute("utilisateurActif", accountService.utilisateurActif());
        return "index";
    }
}