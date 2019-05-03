package pl.kuba565.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kuba565.model.Book;
import pl.kuba565.repository.InMemoryLibraryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryBookController {
    private InMemoryLibraryRepository inMemoryLibraryRepository;

    public CategoryBookController(InMemoryLibraryRepository inMemoryLibraryRepository) {
        this.inMemoryLibraryRepository = inMemoryLibraryRepository;
    }

    @GetMapping("/{categoryName}/books")
    public ResponseEntity<List<Book>> booksByCategory(@PathVariable String categoryName) {
        List<Book> books = inMemoryLibraryRepository.getBooksByCategory(categoryName);

        if (books.size() > 0) {
            return ResponseEntity.ok(books);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
