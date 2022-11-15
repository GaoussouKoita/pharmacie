package ml.workanet.app.pharmacie.securite.config;


import ml.workanet.app.pharmacie.securite.service.AccountService;
import ml.workanet.app.pharmacie.utils.Constante;
import ml.workanet.app.pharmacie.utils.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;


/**
 * Classe de configuration de spring security a base de beans
 * Elle definit deux beans:
 * 1-Pour la securisation des ressources
 * 2-Definit la maniere de charges les users et leurs roles
 */
@EnableWebSecurity
public class SecuriteConfig {

    @Autowired
    private AccountService service;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(service);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().antMatchers(Endpoint.WHITE_LIST).permitAll().
                and().authorizeRequests().antMatchers(Endpoint.ROOT_ROLE_ACCESS).hasAuthority(Constante.ROOT_ROLE).
                and().authorizeRequests().antMatchers(Endpoint.ADMINSTRATEUR_ROLE_ACCESS).hasAuthority(Constante.ADMINISTRATEUR_ROLE).
                and().authorizeRequests().antMatchers(Endpoint.UTILISATEUR_ROLE_ACCESS).hasAuthority(Constante.UTILISATEUR_ROLE).
                and().authorizeRequests().anyRequest().authenticated().
                and().formLogin().loginPage(Endpoint.LOGIN).permitAll();
        /*.
                and().logout().deleteCookies(Constante.SESSION_ID).
                and().rememberMe().key(Constante.KEY).
                and().sessionManagement(session -> {
            session.maximumSessions(1);
        });*/
        return http.build();


    }


}
