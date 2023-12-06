package lt.codeacademy.javau7.tournament_organizer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.javau7.tournament_organizer.utils.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    //UserRole is an ENUM located in the utils package
    private UserRole userRole;
    private String username;
    private String email;
    private String password;
}