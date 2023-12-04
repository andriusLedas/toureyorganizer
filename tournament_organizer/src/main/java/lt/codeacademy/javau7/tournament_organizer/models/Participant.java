package lt.codeacademy.javau7.tournament_organizer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    public Participant(String name, Tournament tournament) {
        this.name = name;
        this.tournament = tournament;
    }

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @OneToMany(mappedBy = "participant1")
    private List<Match> matchesAsParticipant1;

    @OneToMany(mappedBy = "participant2")
    private List<Match> matchesAsParticipant2;
}
