package ml.workanet.app.pharmacie.repository;

import ml.workanet.app.pharmacie.domaine.Approvision;
import ml.workanet.app.pharmacie.domaine.Pharmacie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ApprovisionRepository extends JpaRepository<Approvision, Long> {
    Page<Approvision> findByDateBetweenAndPharmacie(LocalDate dateDebut, LocalDate dateFIn, Pharmacie pharmacie, Pageable pageable);
    Page<Approvision> findByPharmacie(Pharmacie pharmacie, Pageable pageable);

}