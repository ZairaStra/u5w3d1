package zairastra.u5w3d1.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import zairastra.u5w3d1.entities.enums.Role;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Employee implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id", nullable = false)
    @Setter(AccessLevel.NONE)
    private Long id;
    @NotEmpty(message = "Username is required")
    private String username;
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Surname is required")
    private String surname;
    @Email
    @NotEmpty(message = "Email is required")
    private String email;

    private String avatar;

    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;


    public Employee(String username, String name, String surname, String email, String avatar, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.avatar = "https://ui-avatars.com/api/?name=" + name + "+" + surname;
        this.password = password;
    }

    //per ottenere la lista di Role sotto forma di stringhe(da enum) sfrutto questa implementazione ch eho già pronta
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

}
