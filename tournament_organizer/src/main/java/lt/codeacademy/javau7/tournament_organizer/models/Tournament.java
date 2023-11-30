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

    //TODO: add a list of participants
    //TODO: add Participant winner?

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    @JsonIgnore
    private User organizer;

    @OneToMany(mappedBy = "tournament")
    private List<Stage> stages;
}
