package lt.codeacademy.javau7.tournament_organizer.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MatchDTO {

    private String participant2;
    private String participant1;

    public MatchDTO(String participant1, String participant2) {
        this.participant1 = participant1;
        this.participant2 = participant2;
    }

}
