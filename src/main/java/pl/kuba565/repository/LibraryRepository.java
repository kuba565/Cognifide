package pl.kuba565.repository;

import pl.kuba565.model.Author;
import pl.kuba565.model.Book;

import java.util.List;

public interface LibraryRepository {

    Book getBookByIsbn(String isbn);

    List<Book> getBooksByCategory(String categoryName);

    List<Author> getAuthors();
}
