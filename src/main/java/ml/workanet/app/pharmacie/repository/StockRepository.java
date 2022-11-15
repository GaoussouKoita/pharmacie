package ml.workanet.app.pharmacie.repository;

import ml.workanet.app.pharmacie.domaine.Medicament;
import ml.workanet.app.pharmacie.domaine.Pharmacie;
import ml.workanet.app.pharmacie.domaine.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByMedicamentAndPharmacie(Medicament medicament, Pharmacie pharmacie);

    Page<Stock> findByMedicamentNomContaining(String nom, Pageable pageable);
}
