package ml.workanet.app.pharmacie.repository;

import ml.workanet.app.pharmacie.domaine.Depense;
import ml.workanet.app.pharmacie.domaine.Pharmacie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DepenseRepository extends JpaRepository<Depense, Long> {
    public Page<Depense> findByDetailsContainingAndPharmacie(String details, Pharmacie pharmacie ,Pageable date);
    public Page<Depense> findByPharmacie(Pharmacie pharmacie ,Pageable date);
    public List<Depense> findByDateBetweenAndPharmacie (LocalDate dateDebut, LocalDate dateFin, Pharmacie pharmacie);
}