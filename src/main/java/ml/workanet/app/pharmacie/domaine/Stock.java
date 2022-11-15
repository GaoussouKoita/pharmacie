package ml.workanet.app.pharmacie.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 1, message = "Veuillez inserer la quantite du produit >= 1 svp !")
    private int quantite;
    @ManyToOne
    private Medicament medicament;
    @ManyToOne
    private Pharmacie pharmacie;
}
