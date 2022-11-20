package ml.workanet.app.pharmacie.repository;

import ml.workanet.app.pharmacie.domaine.Depense;
import ml.workanet.app.pharmacie.domaine.Pharmacie;
import ml.workanet.app.pharmacie.domaine.Salaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalaireRepository extends JpaRepository<Salaire, Long> {
    Page<Salaire> findByUtilisateurNomContainingAndPharmacie(String nom, Pharmacie pharmacie,Pageable pageable);

    Page<Salaire> findByPharmacie(Pharmacie pharmacie, Pageable pageable);
    List<Salaire> findByPharmacie(Pharmacie pharmacie);
}