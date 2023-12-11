package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.dto.StageDTO;
import lt.codeacademy.javau7.tournament_organizer.dto.TournamentDTO;
import lt.codeacademy.javau7.tournament_organizer.exceptions.TournamentNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.exceptions.UserNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.models.Stage;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;
import lt.codeacademy.javau7.tournament_organizer.models.User;
import lt.codeacademy.javau7.tournament_organizer.repositories.TournamentRepository;
import lt.codeacademy.javau7.tournament_organizer.utils.StageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TournamentServiceImpl implements TournamentService{

    TournamentRepository tournamentRepository;
    UserService userService;
    StageHelper stageHelper;

    public TournamentServiceImpl(TournamentRepository tournamentRepository,
                                 UserService userService,
                                 StageHelper stageHelper)
    {
        this.tournamentRepository = tournamentRepository;
        this.userService = userService;
        this.stageHelper = stageHelper;
    }

    @Override
    @Transactional
    public void createTournament(Long userId, TournamentDTO tournamentDTO) throws UserNotFoundException{
       User organizer = userService.getById(userId);

        Tournament tournament = new Tournament (
                tournamentDTO.getName(),
                tournamentDTO.getNumParticipants(),
                organizer);

        List<Stage> stages = stageHelper.createStagesAndMatches(tournament);
        tournament.setStages(stages);

            tournamentRepository.save(tournament);
            organizer.getOrganizedTournaments().add(tournament);
            userService.saveUser(organizer);
    }

    @Override
    public void saveTournament(Tournament tournament) {tournamentRepository.save(tournament);
    }
    @Override
    public Tournament getById(Long tournamentId) throws TournamentNotFoundException {
        return tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with ID: " + tournamentId + " not found"));
    }

    @Override
    public List<TournamentDTO> getAllUserTournaments(Long userId) throws Exception {
        User user = userService.getById(userId);
        List<Tournament> tournaments = tournamentRepository.findByOrganizerId(user.getId());

        if (tournaments.isEmpty()) {
            throw new Exception("There are no tournaments for user with ID: " + user.getId());
        }
        return tournaments.stream()
                .map(tournament -> new TournamentDTO(tournament.getId(),
                                                     tournament.getName(),
                                                     tournament.getNumParticipants()))
                .collect(Collectors.toList());
    }

    @Override
    public List<TournamentDTO> getAllTournaments() {
        List<Tournament> tournaments = tournamentRepository.findAll();

        return tournaments.stream()
                .map(tournament -> new TournamentDTO(tournament.getId(),
                                                     tournament.getName(),
                                                     tournament.getNumParticipants(),
                                                     tournament.getOrganizer().getUsername()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateTournament(Long tournamentId, TournamentDTO updatedTournamentDTO) throws TournamentNotFoundException {
        Tournament existingTournament = getById(tournamentId);
        existingTournament.setName(updatedTournamentDTO.getName());
        tournamentRepository.save(existingTournament);
    }

    @Override
    @Transactional
    public void addParticipants(Long tournamentId, List<String> participants) throws TournamentNotFoundException {
        Tournament tournament = getById(tournamentId);
        int expectedParticipants = tournament.getNumParticipants();

        if (participants.size() != expectedParticipants) {
            throw new IllegalArgumentException("Number of participants " +
                    "does not match the expected number for the tournament: " + tournament.getNumParticipants());
        }

        tournament.getParticipants().addAll(participants);
        tournamentRepository.save(tournament);
    }

    @Override
    @Transactional
    public void deleteTournament(Long tournamentId) throws TournamentNotFoundException {
        Tournament tournament = getById(tournamentId);
        tournamentRepository.delete(tournament);
    }

    @Override
    public List<StageDTO> getStageAndMatchData(Long tournamentId) throws TournamentNotFoundException {
        Tournament tournament = getById(tournamentId);

        return tournament.getStages().stream()
                .map(StageDTO::new)
                .collect(Collectors.toList());
    }
}

