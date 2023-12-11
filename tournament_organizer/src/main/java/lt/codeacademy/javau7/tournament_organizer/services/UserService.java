package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.dto.UserDTO;
import lt.codeacademy.javau7.tournament_organizer.exceptions.InvalidCredentialsException;
import lt.codeacademy.javau7.tournament_organizer.exceptions.UserNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.models.User;

import java.util.List;


public interface UserService {


    void createUser(UserDTO user);

    void saveUser(User user);

    Long authenticateUser(UserDTO userDTO) throws InvalidCredentialsException;

    User getById(Long id);

    List<UserDTO> getAllUsers();

    @Transactional
    void updateUser(Long id, UserDTO updatedUserDTO) throws UserNotFoundException;

    void deleteUser(Long id);


}
