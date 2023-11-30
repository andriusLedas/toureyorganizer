package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;
import lt.codeacademy.javau7.tournament_organizer.models.User;
import lt.codeacademy.javau7.tournament_organizer.repositories.TournamentRepository;
import lt.codeacademy.javau7.tournament_organizer.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TournamentServiceImpl implements TournamentService{

    TournamentRepository tournamentRepository;
    UserRepository userRepository;

    public TournamentServiceImpl(TournamentRepository tournamentRepository, UserRepository userRepository) {
        this.tournamentRepository = tournamentRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void createTournament(Long userId, Tournament tournament) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            tournament.setOrganizer(user);
            tournamentRepository.save(tournament);
            user.getOrganizedTournaments().add(tournament);
            userRepository.save(user);

        }   else {
            throw new Exception("User with ID " + userId + " not found");
        }
    }

    @Override
    public List<Tournament> getAllUserTournaments(Long userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);

        return optionalUser.map(user ->
                        tournamentRepository.findByOrganizerId(userId))
                .orElseThrow(() -> new Exception(
                        "No tournaments found for user with ID: " + userId));
    }
}

