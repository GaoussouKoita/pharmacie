package ml.workanet.app.pharmacie.repository;

import ml.workanet.app.pharmacie.domaine.Pharmacie;
import ml.workanet.app.pharmacie.domaine.Vente;
import ml.workanet.app.pharmacie.securite.entity.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {

    List<Vente> findByDateBetweenAndPharmacie(LocalDate dateDebut, LocalDate dateFin, Pharmacie pharmacie);
    List<Vente> findByDateAndUtilisateur(LocalDate date, Utilisateur utilisateur);

    List<Vente> findDistinctUtilisateurByDateAndPharmacie(LocalDate date, Pharmacie pharmacie);

    Page<Vente> findByDateBetweenAndPharmacie(LocalDate dateDebut, LocalDate dateFin, Pharmacie pharmacie, Pageable pageable);

    Page<Vente> findByPharmacie(Pharmacie pharmacie, Pageable pageable);
}