package ml.workanet.app.pharmacie.securite.config;

import ml.workanet.app.pharmacie.domaine.Pharmacie;
import ml.workanet.app.pharmacie.domaine.Salaire;
import ml.workanet.app.pharmacie.securite.entity.Role;
import ml.workanet.app.pharmacie.securite.entity.Utilisateur;
import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.service.PharmacieService;
import ml.workanet.app.pharmacie.service.SalaireService;
import ml.workanet.app.pharmacie.utils.Constante;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class Beans {


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

   /* @Bean
    public CommandLineRunner commandLineRunner(AccountService accontService, PharmacieService pharmacieService) {
        return args -> {
            Pharmacie pharmacie = pharmacieService.ajouter(new Pharmacie(1L, "Baguini", "Baguineda", 76666666L));
            Role role = new Role(1L, Constante.ROOT_ROLE);
            Role role1 = new Role(2L, Constante.ADMINISTRATEUR_ROLE);
            Role role2 = new Role(3L, Constante.EMPLOYE_ROLE);
            List<Role> roles = new ArrayList<>();
            roles.add(role);
            roles.add(role1);
            roles.add(role2);

            for (Role r : roles) {
                accontService.ajouterRole(r);
            }
            accontService.ajouterUtilisateur(new Utilisateur(1L, "KOITA", "Gaoussou", "Baguineda",
                    76684788L, "admin@g", "1234","1234" ,roles, pharmacie));

            roles.remove(role);
            accontService.ajouterUtilisateur(new Utilisateur(2L, "BRIBAUD", "Yannick", "Dakar",
                    773332211L, "user@g", "1234", "1234",roles,pharmacie));
        };
    }
*/

}
