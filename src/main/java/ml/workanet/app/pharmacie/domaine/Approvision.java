package ml.workanet.app.pharmacie.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.workanet.app.pharmacie.securite.entity.Utilisateur;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Approvision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float montant;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date = LocalDate.now();
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime heure = LocalTime.now();

    @ManyToOne
    private Pharmacie pharmacie;
    @ManyToOne
    private Utilisateur utilisateur;
    @ManyToMany
    private List<ES_Medicament> medicaments=new ArrayList<>();
}
