package ml.workanet.app.pharmacie.repository;

import ml.workanet.app.pharmacie.domaine.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
}