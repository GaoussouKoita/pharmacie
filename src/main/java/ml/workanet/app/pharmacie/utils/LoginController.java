package ml.workanet.app.pharmacie.utils;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LoginController {


    @GetMapping(Endpoint.LOGIN)
    public String login() {
        log.info("Page d'Authentification");
        return "acceuil/login";
    }
}
