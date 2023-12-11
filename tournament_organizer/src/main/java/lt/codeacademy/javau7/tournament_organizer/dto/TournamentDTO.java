package lt.codeacademy.javau7.tournament_organizer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TournamentDTO {

    private Long id;
    private String name;
    private int numParticipants;
    private String organizerUsername;

    // constructor for posting a new Tournament
    public TournamentDTO(String name, int numParticipants) {
        this.name = name;
        this.numParticipants = numParticipants;
    }

    public TournamentDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public TournamentDTO(Long id, String name, String organizerUsername) {
        this.id = id;
        this.name = name;
        this.organizerUsername = organizerUsername;
    }

    public TournamentDTO(Long id, String name, int numParticipants) {
        this.id = id;
        this.name = name;
        this.numParticipants = numParticipants;
    }

    public TournamentDTO(Tournament tournament) {
        this.id = tournament.getId();
        this.name = tournament.getName();
        this.numParticipants = tournament.getNumParticipants();
        this.organizerUsername = tournament.getOrganizer().getUsername();
    }

    public static List<TournamentDTO> mapToDTOList(List<Tournament> tournaments) {
        return tournaments.stream()
                .map(TournamentDTO::new
                )
                .toList();
    }
}
