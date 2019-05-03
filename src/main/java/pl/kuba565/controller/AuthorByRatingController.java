package pl.kuba565.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kuba565.model.Author;
import pl.kuba565.repository.InMemoryLibraryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/rating")
public class AuthorByRatingController {
    private InMemoryLibraryRepository inMemoryLibraryRepository;

    public AuthorByRatingController(InMemoryLibraryRepository inMemoryLibraryRepository) {
        this.inMemoryLibraryRepository = inMemoryLibraryRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Author>> authorsByAuthorRatingDesc() {
        List<Author> authors = inMemoryLibraryRepository.getAuthors();

        if (authors.size() > 0) {
            return ResponseEntity.ok(authors);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
