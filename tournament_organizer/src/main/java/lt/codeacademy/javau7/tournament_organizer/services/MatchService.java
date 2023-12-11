package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.dto.MatchDTO;
import lt.codeacademy.javau7.tournament_organizer.dto.MatchResultDTO;
import lt.codeacademy.javau7.tournament_organizer.models.Match;
import lt.codeacademy.javau7.tournament_organizer.models.Stage;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;


public interface MatchService {

    @Transactional
    Match createAndSaveMatch(Stage stage, String participant1, String participant2);

    @Transactional
    void generateStage1Matches(Tournament tournament);

    Match getById(Long id);

    Match getNextStageMatch(Match match);

    @Transactional
    void updateMatch(Long id, MatchDTO matchDTO);


    @Transactional
    Match updateResult(Long id, MatchResultDTO resultDTO);

    @Transactional
    Match updateNextStageMatch(Match match);


    boolean hasWinner(MatchResultDTO matchResultDTO);

    boolean winnerBelongsToMatch(Long matchId, MatchResultDTO resultDTO);

    boolean finalMatch(Match match);

    boolean teamInAnotherMatchOfSameStage(Match match);
}
