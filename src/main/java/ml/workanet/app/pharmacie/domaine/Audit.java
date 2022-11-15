package ml.workanet.app.pharmacie.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.workanet.app.pharmacie.securite.entity.Utilisateur;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String reference;
    private String action;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date = LocalDate.now();
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime heure = LocalTime.now();

    @ManyToOne
    private Utilisateur utilisateur;
    @ManyToOne
    private Pharmacie  pharmacie;

    public Audit(String reference, String action) {
        this.reference = reference;
        this.action = action;
    }
}
