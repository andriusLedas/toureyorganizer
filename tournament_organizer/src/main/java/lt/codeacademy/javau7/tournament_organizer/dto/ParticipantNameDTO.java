package lt.codeacademy.javau7.tournament_organizer.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ParticipantNameDTO {
    private List<String> participantNames;

    @JsonCreator
    public ParticipantNameDTO(@JsonProperty("participantNames") List<String> participantNames) {
        this.participantNames = participantNames;
    }

}
