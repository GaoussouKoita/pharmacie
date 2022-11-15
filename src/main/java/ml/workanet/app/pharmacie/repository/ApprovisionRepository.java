package ml.workanet.app.pharmacie.repository;

import ml.workanet.app.pharmacie.domaine.Approvision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApprovisionRepository extends JpaRepository<Approvision, Long> {
}