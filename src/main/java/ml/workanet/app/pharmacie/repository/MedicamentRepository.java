package ml.workanet.app.pharmacie.repository;

import ml.workanet.app.pharmacie.domaine.Medicament;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {

    Page<Medicament> findByNomContaining(String nom, Pageable pageable);
}