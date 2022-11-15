package ml.workanet.app.pharmacie.repository;


import ml.workanet.app.pharmacie.domaine.ES_Medicament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ES_MedicamentRepository extends JpaRepository<ES_Medicament, Long> {
}