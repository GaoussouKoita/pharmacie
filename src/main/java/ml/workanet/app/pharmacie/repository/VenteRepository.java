package ml.workanet.app.pharmacie.repository;

import ml.workanet.app.pharmacie.domaine.Vente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface VenteRepository extends JpaRepository<Vente, Long> {
    Page<Vente> findByDateBetween(LocalDate dateDebut, LocalDate dateFIn, Pageable pageable);
}