package ml.workanet.app.pharmacie.securite.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ml.workanet.app.pharmacie.domaine.Pharmacie;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String adresse;
    @NotNull(message = "Le numero de telephone doit etre sans espace")
    private Long telephone;
    @Size(min = 3, message = "Le login doit contenir au moins 3 caracteres")
    private String email;

    @Size(min = 8, message = "Le password doit contenir au moins 8 caracteres")
    private String password;
    @Transient
    private String confirmation;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
    @ManyToOne
    private Pharmacie pharmacie;

}
