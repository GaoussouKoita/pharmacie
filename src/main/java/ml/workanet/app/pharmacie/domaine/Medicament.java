package ml.workanet.app.pharmacie.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @Min(value = 5, message = "Le prix doit être superieur à 5")
    private long prixVente;
    private long prixAssureur;
    private boolean prisEnCharge=true;
    private String details;
    @ManyToOne
    private Type type;
    @ManyToOne
    private Pharmacie pharmacie;
}
