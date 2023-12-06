package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.models.Match;
import lt.codeacademy.javau7.tournament_organizer.models.Participant;
import lt.codeacademy.javau7.tournament_organizer.models.Stage;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;
import lt.codeacademy.javau7.tournament_organizer.repositories.MatchRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService{

    MatchRepository matchRepository;
    ParticipantService participantService;
    StageService stageService;
    private static final Logger logger = LoggerFactory.getLogger(MatchServiceImpl.class);

    public MatchServiceImpl(MatchRepository matchRepository, ParticipantService participantService, StageService stageService) {
        this.matchRepository = matchRepository;
        this.participantService = participantService;
        this.stageService = stageService;
    }

    @Override
    @Transactional
    public void generateFirstStageMatches(Tournament tournament) {
        List<Participant> participants = tournament.getParticipants();
        List<Stage> stages = tournament.getStages();
        Stage firstStage = stages.get(stages.size() - 1);

        if (participants == null || participants.isEmpty()) {
            logger.warn("Attempting to generate first stage matches with an empty list of participants.");
            return;
        }

        for (int i = 0; i < participants.size(); i += 2) {

            Participant participant1 = participants.get(i);
            Participant participant2 = participants.get(i + 1);
            Match match = new Match(participant1, participant2);
            match.setStage(firstStage);

            participantService.save(participant1);
            participantService.save(participant2);
            stageService.save(firstStage);
            matchRepository.save(match);
        }
    }
}
