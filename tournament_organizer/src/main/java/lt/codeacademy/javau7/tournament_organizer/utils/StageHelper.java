package lt.codeacademy.javau7.tournament_organizer.utils;

import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lt.codeacademy.javau7.tournament_organizer.models.Match;
import lt.codeacademy.javau7.tournament_organizer.models.Stage;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
@NoArgsConstructor
public class StageHelper {

    @Transactional
    public List<Stage> createStagesAndMatches(Tournament tournament) {
        List<Stage> stages = new ArrayList<>();
        int numStages = getNumStages(tournament);
        int numStagePlayers = tournament.getNumParticipants();

        for (int i = 1; i <= numStages; i++) {
            Stage stage = new Stage("1/" + numStagePlayers / 2);
            List<Match> matches = stage.getMatches();

            for (int j = 1; j <= numStagePlayers / 2; j++) {
                Match match = new Match();
                match.setStage(stage);
                matches.add(match);
            }

            stage.setTournament(tournament);
            stages.add(stage);
            numStagePlayers /= 2;
        }
        return stages;
    }

    private int getNumStages(Tournament tournament) {
        int numberOfParticipants = tournament.getNumParticipants();

        return switch(numberOfParticipants) {
            case 4 -> 2;
            case 8 -> 3;
            case 16 -> 4;
            case 32 -> 5;
            case 64 -> 6;
            case 128 -> 7;
            default -> throw new IllegalStateException("Unexpected value: " + numberOfParticipants);
        };
    }
}
