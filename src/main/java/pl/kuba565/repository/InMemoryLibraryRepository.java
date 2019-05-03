package pl.kuba565.repository;

import org.springframework.stereotype.Component;
import pl.kuba565.model.Author;
import pl.kuba565.model.Book;
import pl.kuba565.service.FileToLibraryMapperService;

import java.util.ArrayList;
import java.util.List;

@Component
public class InMemoryLibraryRepository implements LibraryRepository {
    private List<Book> books;
    private List<Author> authors;

    public InMemoryLibraryRepository(FileToLibraryMapperService fileToLibraryMapperService) {
        this.books = fileToLibraryMapperService.mapJsonToBookList();
        this.authors = fileToLibraryMapperService.mapJsonToAuthorList();
    }

    @Override
    public Book getBookByIsbn(String isbn) {

        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return book;
            }
        }

        return null;
    }

    @Override
    public List<Book> getBooksByCategory(String categoryName) {
        List<Book> result = new ArrayList<>();

        books.forEach(book -> {
            List<String> categories = book.getCategories();
            if (categories != null && categories.contains(categoryName)) {
                result.add(book);
            }
        });
        return result;
    }

    @Override
    public List<Author> getAuthorsOrderByAuthorRatingDesc() {
        List<Author> result;

        result = authors;

        return result;
    }

}
