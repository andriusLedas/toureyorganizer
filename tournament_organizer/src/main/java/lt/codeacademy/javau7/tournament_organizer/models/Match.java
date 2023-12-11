package lt.codeacademy.javau7.tournament_organizer.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;

    private String participant1 = "";
    private String participant2 = "";
    private String winner = "";
    private int participant1Score = -1;
    private int participant2Score = -1;

    public Match(String participant1, String participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
    }
}
