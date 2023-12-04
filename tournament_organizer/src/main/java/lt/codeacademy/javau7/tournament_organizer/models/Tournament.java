package lt.codeacademy.javau7.tournament_organizer.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int numParticipants;

    //TODO: add Participant winner?

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    @JsonIgnore
    private User organizer;

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.PERSIST)
    private List<Stage> stages;

    // TODO: corresponding methods in Service and Controller
    @OneToMany(mappedBy = "tournament")
    private List<Participant> participants;

}
