package ml.workanet.app.pharmacie.securite.service;


import ml.workanet.app.pharmacie.securite.entity.ChangePassword;
import ml.workanet.app.pharmacie.securite.entity.Role;
import ml.workanet.app.pharmacie.securite.entity.Utilisateur;
import ml.workanet.app.pharmacie.securite.repository.RoleRepository;
import ml.workanet.app.pharmacie.securite.repository.UtilisateurRepository;
import ml.workanet.app.pharmacie.utils.Constante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Transactional
public class AccountService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    public PasswordEncoder passwordEncoder;



    public Utilisateur ajouterUtilisateur(Utilisateur utilisateur) {
        utilisateur.setPassword(passwordEncoder.encode(utilisateur.getPassword()));
        if (!estRoot()) {
            utilisateur.setPharmacie(utilisateurActif().getPharmacie());
        }
        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur rechercherParEmail(String email) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        return utilisateur;
    }

    public void desactiver(Long id) {
        Utilisateur utilisateur = rechercher(id);
        utilisateur.setRoles(null);
        utilisateurRepository.save(utilisateur);
    }

    public Utilisateur rechercher(Long id) {
        Utilisateur utilisateur = utilisateurRepository.findById(id).get();
        return utilisateur;
    }

    public List<Utilisateur> lister() {

        return utilisateurRepository.findAll(Sort.by("nom").ascending());
    }

    public Page<Utilisateur> lister(int page) {
        return utilisateurRepository.findAll(PageRequest.of(page, Constante.NBRE_PAR_PAGE,
                Sort.by("prenom").ascending().and(Sort.by("nom").ascending())));
    }

    public Page<Utilisateur> lister(String nom, int page) {
        return utilisateurRepository.findByNomContaining(nom, PageRequest.of(page, Constante.NBRE_PAR_PAGE,
                Sort.by("prenom").ascending().and(Sort.by("nom").ascending())));
    }

    public void ajouterRole(Role role) {
        roleRepository.save(role);
    }

    public List<Role> listerRole() {
        return roleRepository.findAll();
    }


    public Utilisateur utilisateurActif() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        return utilisateur;
    }

    public void modifierPassword(String email, String newPassword) {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email);
        String newPasswordEncode = passwordEncoder.encode(newPassword);
        utilisateurRepository.updateUtilisateurByEmail(email, newPasswordEncode);
    }

    public int verifierPassword(ChangePassword changePassword) {

        Utilisateur user = utilisateurActif();
        String oldPassword = changePassword.getOldPassword();
        String newPassword = changePassword.getNewPassword();
        String confirmation = changePassword.getConfirmation();

        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            if (newPassword.equals(confirmation)) {
                modifierPassword(user.getEmail(), newPassword);
                return 0;
            } else { //No match new password and confirmatin
                return 1;
            }
        }
        //No match old password
        return -1;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur = rechercherParEmail(username);
        List<Role> roles = utilisateur.getRoles();
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(r -> {
            authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
        });
        return new User(utilisateur.getEmail(), utilisateur.getPassword(), authorities);
    }

    public boolean estRoot(){
        AtomicBoolean estRoot = new AtomicBoolean(false);
        utilisateurActif().getRoles().forEach(role -> {
            if (role.getRoleName().equals("ROOT")) {
                estRoot.set(true);
            }
        });
        return estRoot.get();
    }



}
