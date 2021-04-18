package pl.sztukakodu.bookaro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.sztukakodu.bookaro.catalog.application.CatalogController;
import pl.sztukakodu.bookaro.catalog.domain.Book;

import java.util.List;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogController catalogController;
    private final String query;
    private final Long limit;

    public ApplicationStartup(CatalogController catalogController,
                              @Value("${bookaro.catalog.query}") String query,
                              @Value("${bookaro.catalog.limit:1}") Long limit) {
        this.catalogController = catalogController;
        this.query = query;
        this.limit = limit;

    }

    @Override
    public void run(String... args){
        List<Book> byTitle = catalogController.findByTitle(query);
        if (byTitle.size() > 0) {
            byTitle.stream().limit(limit).forEach(System.out::println);
        } else {
            System.out.println("Nie znaleziono w tytułach");
        }

        List<Book> byAuthor = catalogController.findByAuthor(query);
        if (byAuthor.size() > 0) {
            byAuthor.stream().limit(limit).forEach(System.out::println);
        } else {
            System.out.println("Nie znaleziono w authorach");
        }
    }
}
