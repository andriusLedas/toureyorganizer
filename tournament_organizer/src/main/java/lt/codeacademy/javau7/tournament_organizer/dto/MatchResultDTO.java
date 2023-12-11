package lt.codeacademy.javau7.tournament_organizer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MatchResultDTO {

    private int participant1Score;
    private int participant2Score;
    String winner;

    // dummy comment for commit
}
