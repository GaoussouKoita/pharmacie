package ml.workanet.app.pharmacie.securite.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassword {
    private String oldPassword;
    @Size(min = 8, message = "Le password doit contenir au moins 8 caracteres")
    private String newPassword;
    @Size(min = 8, message = "Le password doit contenir au moins 8 caracteres")
    private String confirmation;
}
