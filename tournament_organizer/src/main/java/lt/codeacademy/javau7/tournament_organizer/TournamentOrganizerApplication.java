package lt.codeacademy.javau7.tournament_organizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@SpringBootApplication
public class TournamentOrganizerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TournamentOrganizerApplication.class, args);
	}

}
