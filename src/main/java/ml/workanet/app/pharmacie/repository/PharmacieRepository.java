package ml.workanet.app.pharmacie.repository;

import ml.workanet.app.pharmacie.domaine.Pharmacie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacieRepository extends JpaRepository<Pharmacie, Long> {
}
