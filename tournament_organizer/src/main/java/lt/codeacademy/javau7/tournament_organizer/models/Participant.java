package lt.codeacademy.javau7.tournament_organizer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Entity
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    // TODO: The below two columns are a bit goofy...

    @OneToMany(mappedBy = "participant1")
    private List<Match> matchesAsParticipant1;

    @OneToMany(mappedBy = "participant2")
    private List<Match> matchesAsParticipant2;
}
