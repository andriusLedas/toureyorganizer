package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.models.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {


    void createUser(User user);

    User findById(Long id);

    List<User> getAllUsers();

    @Transactional
    void updateUser(Long id, User updatedUser);

    void deleteUser(Long id);


}
