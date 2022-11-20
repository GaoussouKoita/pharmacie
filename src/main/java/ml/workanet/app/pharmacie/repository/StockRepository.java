package ml.workanet.app.pharmacie.repository;

import ml.workanet.app.pharmacie.domaine.Medicament;
import ml.workanet.app.pharmacie.domaine.Pharmacie;
import ml.workanet.app.pharmacie.domaine.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByMedicamentAndPharmacie(Medicament medicament, Pharmacie pharmacie);

    Page<Stock> findByMedicamentNomContainingAndPharmacie(String nom, Pharmacie pharmacie, Pageable pageable);

    List<Stock> findByPharmacie(Pharmacie pharmacie, Sort sort);
    Page<Stock> findByPharmacie(Pharmacie pharmacie, Pageable pageable);
}
