package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.dto.MatchDTO;
import lt.codeacademy.javau7.tournament_organizer.dto.MatchResultDTO;
import lt.codeacademy.javau7.tournament_organizer.exceptions.MatchNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.exceptions.TeamAlreadyInAnotherMatchException;
import lt.codeacademy.javau7.tournament_organizer.models.*;
import lt.codeacademy.javau7.tournament_organizer.repositories.MatchRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService{

    MatchRepository matchRepository;
    StageService stageService;
    private static final Logger logger = LoggerFactory.getLogger(MatchServiceImpl.class);

    public MatchServiceImpl(MatchRepository matchRepository, StageService stageService) {
        this.matchRepository = matchRepository;
        this.stageService = stageService;
    }

    @Override
    @Transactional
    public Match createAndSaveMatch(Stage stage, String participant1, String participant2) {
        Match match = new Match(participant1, participant2);
        match.setStage(stage);
        stageService.save(stage);
        matchRepository.save(match);

        return match;
    }

    @Override
    @Transactional
    public void generateStage1Matches(Tournament tournament) {
        List<String> participants = tournament.getParticipants();
        Stage firstStage = tournament.getStages().get(0);
        List<Match> stageMatches = firstStage.getMatches();
        stageMatches.clear();

        if (participants == null || participants.isEmpty()) {
            logger.warn("Attempted to generate first stage matches with an empty list of participants.");
            return;
        }

        for (int i = 0; i < participants.size(); i += 2) {
            String participant1 = participants.get(i);
            String participant2 = participants.get(i + 1);
            Match match = createAndSaveMatch(firstStage, participant1, participant2);
            stageMatches.add(match);
        }
    }

    @Override
    public Match getById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new MatchNotFoundException("Match with id " + id + " not found"));
    }

    @Override
    public Match getNextStageMatch(Match match) {
        Stage currentStage = match.getStage();
        Tournament tournament = currentStage.getTournament();
        int indexOfCurrentStage = tournament.getStages().indexOf(currentStage);
        Stage nextStage = tournament.getStages().get(indexOfCurrentStage + 1);

        List<Match> currentStageMatches = currentStage.getMatches();
        int indexInNextStage = currentStageMatches.indexOf(match) / 2;

        return nextStage.getMatches().get(indexInNextStage);
    }

    @Override
    @Transactional
    public void updateMatch(Long id, MatchDTO matchDTO) {
        Match match = getById(id);

        if (teamInAnotherMatchOfSameStage(match)) {
            throw new TeamAlreadyInAnotherMatchException("Cannot update match." +
                    " A team is already present in another match of the same stage.");
        }

        match.setParticipant1(matchDTO.getParticipant1());
        match.setParticipant2(matchDTO.getParticipant2());
        matchRepository.save(match);
    }

    @Override
    @Transactional
    public void updateResult(Long id, MatchResultDTO resultDTO) {
        Match match = getById(id);

        if (winnerBelongsToMatch(id, resultDTO)) {
            match.setParticipant1Score(resultDTO.getParticipant1Score());
            match.setParticipant2Score(resultDTO.getParticipant2Score());
            match.setWinner(resultDTO.getWinner());

            if (!finalMatch(match)) {
                updateNextStageMatch(match);
            }
            matchRepository.save(match);
        }
    }

    @Override
    @Transactional
    public void updateNextStageMatch(Match match) {
        if (hasWinner(match)){
            String winner = match.getWinner();
            Match nextStageMatch = getNextStageMatch(match);
            Stage nextStage = nextStageMatch.getStage();

            if (winner.equalsIgnoreCase(match.getParticipant1())) {
                nextStageMatch.setParticipant1(winner);
            } else {
                nextStageMatch.setParticipant2(winner);
            }

            matchRepository.save(nextStageMatch);
            stageService.save(nextStage);
        }
    }

    @Override
    public boolean hasWinner(Match match) {
        return !match.getWinner().isEmpty();
    }

    @Override
    public boolean winnerBelongsToMatch(Long matchId, MatchResultDTO resultDTO) {
        Match match = getById(matchId);
        String participant1 = match.getParticipant1();
        String participant2 = match.getParticipant2();
        String winner = resultDTO.getWinner();

        if (!winner.isEmpty() && (participant1.equalsIgnoreCase(winner)) ||
                                 (participant2.equalsIgnoreCase(winner)))
            return true;

        throw new IllegalArgumentException("Invalid winner provided:" +
                " does not match either participant or is empty.");
    }

    @Override
    public boolean finalMatch(Match match) {
        int numMatches =  match.getStage().getMatches().size();
        return numMatches == 1;
    }

    @Override
    public boolean teamInAnotherMatchOfSameStage(Match match) {
        Stage stage = match.getStage();
        return stage.getMatches().stream()
                .anyMatch(e -> e != match &&
                        (e.getParticipant1().equalsIgnoreCase(match.getParticipant1()) ||
                                e.getParticipant2().equalsIgnoreCase(match.getParticipant1()) ||
                                e.getParticipant1().equalsIgnoreCase(match.getParticipant2()) ||
                                e.getParticipant2().equalsIgnoreCase(match.getParticipant2())));
    }
}

