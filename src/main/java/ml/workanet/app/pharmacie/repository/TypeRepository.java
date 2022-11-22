package ml.workanet.app.pharmacie.repository;

import ml.workanet.app.pharmacie.domaine.Pharmacie;
import ml.workanet.app.pharmacie.domaine.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    Page<Type> findByPharmacie(Pharmacie pharmacie, Pageable pageable);
    List<Type> findByPharmacie(Pharmacie pharmacie, Sort sort);
}