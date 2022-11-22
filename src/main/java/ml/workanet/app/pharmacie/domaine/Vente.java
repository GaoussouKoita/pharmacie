package ml.workanet.app.pharmacie.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.workanet.app.pharmacie.securite.entity.Utilisateur;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int nbreMedicament;
    @Min(value = 5, message = "Le montantAPayer doit être superieur à 5")
    private long montantAPayer;
    private long montantTotal;
    private long partAssureur;
    private boolean type;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date= LocalDate.now();
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime heure= LocalTime.now();

    @ManyToMany
    private List<ES_Medicament> medicaments = new ArrayList<>();
    @ManyToOne
    private Pharmacie pharmacie;
    @ManyToOne
    private Utilisateur utilisateur;
}
