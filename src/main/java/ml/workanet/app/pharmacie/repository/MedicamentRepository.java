package ml.workanet.app.pharmacie.repository;

import ml.workanet.app.pharmacie.domaine.Medicament;
import ml.workanet.app.pharmacie.domaine.Pharmacie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicamentRepository extends JpaRepository<Medicament, Long> {

    Page<Medicament> findByPharmacie(Pharmacie pharmacie, Pageable pageable);
    List<Medicament> findByPharmacie(Pharmacie pharmacie, Sort sort);
    Page<Medicament> findByNomContainingAndPharmacie(String nom, Pharmacie pharmacie, Pageable pageable);
    Medicament findByNomAndPharmacie(String nom, Pharmacie pharmacie);
}