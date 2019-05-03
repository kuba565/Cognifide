package pl.kuba565.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kuba565.model.Book;
import pl.kuba565.repository.InMemoryLibraryRepository;

@RestController
@RequestMapping("/api/book")
public class IsbnBookController {
    private InMemoryLibraryRepository inMemoryLibraryRepository;

    @Autowired
    public IsbnBookController(InMemoryLibraryRepository inMemoryLibraryRepository) {
        this.inMemoryLibraryRepository = inMemoryLibraryRepository;
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Book> bookByIsbn(@PathVariable String isbn) {
        Book book = inMemoryLibraryRepository.getBookByIsbn(isbn);

        if (book != null) {
            return ResponseEntity.ok(book);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
