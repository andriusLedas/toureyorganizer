package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.dto.UserDTO;
import lt.codeacademy.javau7.tournament_organizer.models.User;

import java.util.List;


public interface UserService {


    void createUser(UserDTO user);

    void saveUser(User user);

    User getById(Long id);

    List<User> getAllUsers();

    @Transactional
    void updateUser(Long id, User updatedUser);

    void deleteUser(Long id);


}
