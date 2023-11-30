package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.exceptions.TournamentNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.exceptions.UserNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;
import lt.codeacademy.javau7.tournament_organizer.models.User;
import lt.codeacademy.javau7.tournament_organizer.repositories.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService{

    TournamentRepository tournamentRepository;
    UserService userService;

    public TournamentServiceImpl(TournamentRepository tournamentRepository, UserService userService) {
        this.tournamentRepository = tournamentRepository;
        this.userService = userService;
    }

    @Override
    @Transactional
    public void createTournament(Long userId, Tournament tournament) throws UserNotFoundException{
       User user = userService.getById(userId);

            tournament.setOrganizer(user);
            tournamentRepository.save(tournament);
            user.getOrganizedTournaments().add(tournament);
            userService.saveUser(user);
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

