package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.dto.UserDTO;
import lt.codeacademy.javau7.tournament_organizer.exceptions.UserNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.models.User;
import lt.codeacademy.javau7.tournament_organizer.security.payload.SignupRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<?> registerUser(SignupRequest signUpRequest);

    User getById(Long id);

    List<UserDTO> getAllUsers();

    @Transactional
    void updateUser(Long id, UserDTO updatedUserDTO) throws UserNotFoundException;

    @Transactional
    void deleteUser(Long id) throws UserNotFoundException;

    void saveUser(User user);
}

