package lt.codeacademy.javau7.tournament_organizer.utils;

import lt.codeacademy.javau7.tournament_organizer.models.Stage;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class StageHelper {

    public StageHelper(){}

    public List<Stage> createStages(Tournament tournament) {
        List<Stage> stages = new ArrayList<>();
        int numStages = getNumStages(tournament);

        Stage finalStage = new Stage("final");
        finalStage.setTournament(tournament);
        stages.add(finalStage);

        int stageCounter = 1;
        for (int i = 0; i < numStages-1; i++) {
            Stage stage = new Stage("1/" + stageCounter * 2);
            stage.setTournament(tournament);
            stages.add(stage);
            stageCounter *= 2;
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
