package ml.workanet.app.pharmacie.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.workanet.app.pharmacie.securite.entity.Utilisateur;
import org.springframework.format.annotation.DateTimeFormat;
import sun.util.resources.CalendarData;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 5, message = "Le montant doit être superieur à 5")
    private long montant;

    @DateTimeFormat(pattern = "yyyy-MM")
    private YearMonth mois;

    @ManyToOne
    private Pharmacie pharmacie;
    @ManyToOne
    private Utilisateur utilisateur;

}
