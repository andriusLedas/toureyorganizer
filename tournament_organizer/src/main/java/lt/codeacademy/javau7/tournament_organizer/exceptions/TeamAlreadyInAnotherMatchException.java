package lt.codeacademy.javau7.tournament_organizer.exceptions;

public class TeamAlreadyInAnotherMatchException extends RuntimeException{

    public TeamAlreadyInAnotherMatchException(String message) {
        super(message);
    }
}
