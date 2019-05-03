package pl.kuba565.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pl.kuba565.model.*;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FileToLibraryMapperService {
    private final String dataSource;

    public FileToLibraryMapperService(@Value("${datasource}") String dataSource) {
        this.dataSource = dataSource;
    }

    public List<Book> mapJsonToBookList() {
        List<Book> books = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File file = new File(dataSource);
            BookRequest bookRequest = objectMapper.readValue(file, BookRequest.class);
            List<Item> items = bookRequest.getItems();

            items.forEach(item -> {
                Book book = new Book();
                VolumeInfo volumeInfo = item.getVolumeInfo();
                book.setTitle(volumeInfo.getTitle());
                book.setSubtitle(volumeInfo.getSubtitle());
                book.setPublisher(volumeInfo.getPublisher());
                if (volumeInfo.getPublishedDate() != null) {
                    try {
                        if (volumeInfo.getPublishedDate().length() == 4) {
                            Date publishedDate = new SimpleDateFormat("yyyy").parse(volumeInfo.getPublishedDate());
                            book.setPublishedDate(publishedDate.getTime());
                        } else {
                            Date publishedDate = new SimpleDateFormat("yyyy-MM-dd").parse(volumeInfo.getPublishedDate());
                            book.setPublishedDate(publishedDate.getTime());
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                book.setDescription(volumeInfo.getDescription());
                book.setPageCount(volumeInfo.getPageCount());
                ImageLinks imageLinks = volumeInfo.getImageLinks();
                if (imageLinks != null) {
                    book.setThumbnailUrl(imageLinks.getThumbnail());
                }
                book.setLanguage(volumeInfo.getLanguage());
                book.setPreviewLink(volumeInfo.getPreviewLink());
                book.setAverageRating(volumeInfo.getAverageRating());
                book.setCategories(volumeInfo.getCategories());
                List<String> authors = volumeInfo.getAuthors();
                book.setAuthors(authors);

                List<IndustryIdentifiers> industryIdentifiers = volumeInfo.getIndustryIdentifiers();
                industryIdentifiers.forEach(identifier -> {
                    if (identifier.getType().equals("ISBN_13")) {
                        book.setIsbn(identifier.getIdentifier());
                    }
                });
                if (book.getIsbn() == null) {
                    book.setIsbn(item.getId());
                }
                books.add(book);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Author> mapJsonToAuthorList() {
        List<Author> authors = new ArrayList<>();
        Map<String, List<Double>> authorRatingMap = new HashMap<>();
        List<Book> books = mapJsonToBookList();

        for (Book book : books) {
            List<String> bookAuthors = book.getAuthors();
            Double averageRating = book.getAverageRating();
            List<Double> ratings = new ArrayList<>();

            if (bookAuthors != null) {
                bookAuthors.forEach(currentAuthor -> {
                    if (averageRating != null) {
                        ratings.add(averageRating);
                    }
                    if (ratings.size() > 0) {
                        authorRatingMap.put(currentAuthor, ratings);
                    }
                });
            }
        }

        authorRatingMap.forEach((author, ratings) -> {
            Double sum = 0.0;
            if (ratings != null) {
                for (Double rating : ratings) {
                    if (rating != null) {
                        sum += rating;
                    }
                }
                Double rating = sum / ratings.size();
                Author newAuthor = new Author(author, rating);
                authors.add(newAuthor);
            }
        });

        authors.sort(Comparator.comparing(Author::getAverageRating).reversed());

        return authors;
    }
}
