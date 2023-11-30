package lt.codeacademy.javau7.tournament_organizer.exceptions;

public class TournamentNotFoundException extends RuntimeException{

    public TournamentNotFoundException(String message) {
        super(message);
    }
}
