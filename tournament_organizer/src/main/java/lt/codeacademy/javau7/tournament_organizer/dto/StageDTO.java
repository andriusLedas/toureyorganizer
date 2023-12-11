package lt.codeacademy.javau7.tournament_organizer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.javau7.tournament_organizer.models.Match;
import lt.codeacademy.javau7.tournament_organizer.models.Stage;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class StageDTO {

    private Long id;

    private String name;

    private List<Match> matches = new ArrayList<>();

    public StageDTO(Stage stage) {
        this.id = stage.getId();
        this.name = stage.getName();
        this.matches = stage.getMatches();
    }
}
