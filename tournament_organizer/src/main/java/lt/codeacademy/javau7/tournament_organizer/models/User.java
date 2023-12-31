package lt.codeacademy.javau7.tournament_organizer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lt.codeacademy.javau7.tournament_organizer.enums.UserRole;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRole USERROLE;

    private String username;
    private String email;
    private String password;

    @OneToMany(mappedBy = "organizer", cascade = CascadeType.PERSIST)
    private List<Tournament> organizedTournaments;

}
