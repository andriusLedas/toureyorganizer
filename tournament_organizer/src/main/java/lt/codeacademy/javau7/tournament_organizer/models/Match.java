package lt.codeacademy.javau7.tournament_organizer.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Table(name = "tournament_match")

public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "winner_id")
    private Participant winner;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;

    @ManyToOne
    @JoinColumn(name = "participant1_id")
    private Participant participant1;

    @ManyToOne
    @JoinColumn(name = "participant2_id")
    private Participant participant2;

    public Match (Participant participant1, Participant participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
    }
}
