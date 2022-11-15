package ml.workanet.app.pharmacie.repository;

import ml.workanet.app.pharmacie.domaine.Audit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditRepository extends JpaRepository<Audit, Long> {

    Page<Audit> findByUtilisateurId(Long id, Pageable pageable);
}