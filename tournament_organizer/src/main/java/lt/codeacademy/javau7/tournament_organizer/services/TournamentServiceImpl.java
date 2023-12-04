package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.dtos.ParticipantNameDTO;
import lt.codeacademy.javau7.tournament_organizer.exceptions.TournamentNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.exceptions.UserNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.models.Stage;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;
import lt.codeacademy.javau7.tournament_organizer.models.User;
import lt.codeacademy.javau7.tournament_organizer.repositories.TournamentRepository;
import lt.codeacademy.javau7.tournament_organizer.utils.StageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    public void createTournament(Long userId, Tournament tournament) throws UserNotFoundException{
       User user = userService.getById(userId);
       List<Stage> stages = stageHelper.createStages(tournament);

            tournament.setOrganizer(user);
            tournament.setStages(stages);

            tournamentRepository.save(tournament);
            user.getOrganizedTournaments().add(tournament);
            userService.saveUser(user);
    }

    @Override
    public void saveTournament(Tournament tournament) {tournamentRepository.save(tournament);
    }

    @Override
    public List<Tournament> getAllUserTournaments(Long userId) throws Exception {
        User user = userService.getById(userId);
        List<Tournament> tournaments = tournamentRepository.findByOrganizerId(user.getId());

        if (tournaments.isEmpty()) {
           throw new Exception("There are no tournaments for user with ID: " + user.getId());
        }
        return tournaments;
    }

    @Override
    public List<Tournament> getAllTournaments() {
        List<Tournament> tournaments = tournamentRepository.findAll();
        return tournaments.isEmpty() ? Collections.emptyList() : tournaments;
    }

    @Override
    public Tournament getById(Long tournamentId) throws TournamentNotFoundException {
        return tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament with ID: " + tournamentId + " not found"));
    }

    @Override
    @Transactional
    public void updateTournament(Long tournamentId, Tournament updatedTournament) throws TournamentNotFoundException {
        Tournament existingTournament = getById(tournamentId);
        existingTournament.setName(updatedTournament.getName());
        // Update other fields as needed
        tournamentRepository.save(existingTournament);
    }

    @Override
    @Transactional
    public void deleteTournament(Long tournamentId) throws TournamentNotFoundException {
        Tournament tournament = getById(tournamentId);
        tournamentRepository.delete(tournament);
    }
}

