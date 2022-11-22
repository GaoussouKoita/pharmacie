package ml.workanet.app.pharmacie.service;

import ml.workanet.app.pharmacie.domaine.Pharmacie;
import ml.workanet.app.pharmacie.repository.PharmacieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PharmacieService {
    @Autowired
    private PharmacieRepository repository;

    public Pharmacie ajouter(Pharmacie pharmacie) {
        return repository.save(pharmacie);
    }

    public Pharmacie rechercher(Long id) {
        Pharmacie pharmacie = repository.findById(id).get();
        return pharmacie;
    }

    public void supprimer(Long id) {
        Pharmacie pharmacie = repository.findById(id).get();
        repository.deleteById(id);
    }

    public Page<Pharmacie> lister(int page, int nbreParPage) {

        return repository.findAll(PageRequest.of(page, nbreParPage,
                Sort.by("nom").ascending()));
    }

    public List<Pharmacie> lister() {
        return repository.findAll(
                Sort.by("nom").ascending());
    }


}
