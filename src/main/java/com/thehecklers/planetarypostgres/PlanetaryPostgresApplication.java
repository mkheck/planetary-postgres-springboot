package com.thehecklers.planetarypostgres;

import lombok.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.List;

@SpringBootApplication
public class PlanetaryPostgresApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlanetaryPostgresApplication.class, args);
    }

    @Bean
    CommandLineRunner loadMarks(MarkRepository repo) {
        return args -> {
            // With create+drop, this shouldn't matter...but it's a good practice for dev data loading consistency.
            repo.deleteAll();

            List<Mark> marks = List.of(new Mark("Mark", "Brown"), new Mark("Mark", "Heckler"), new Mark("Mark", "Gatiss"));

            repo.saveAll(marks);

            repo.findAll().forEach(System.out::println);
        };
    }
}

@AllArgsConstructor
@RestController
class MarkController {
    private final MarkRepository repo;

    @GetMapping
    Iterable<Mark> getAllMarks() {
        return repo.findAll();
    }
}

interface MarkRepository extends CrudRepository<Mark, Long> {
}

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
class Mark {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String firstName, lastName;
}