package ml.workanet.app.pharmacie.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private float prixVente;
    private float prixAchat;
    private float prixAssureur;
    private boolean prisEnCharge=true;
    private String details;
    @ManyToOne
    private Type type;
    @ManyToOne
    private Pharmacie pharmacie;
}
