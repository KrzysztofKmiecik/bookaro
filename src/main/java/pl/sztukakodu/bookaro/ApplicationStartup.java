package pl.sztukakodu.bookaro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase;
import pl.sztukakodu.bookaro.catalog.domain.Book;

import java.util.List;

import static pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase.CreateBookCommand;
import static pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase.UpdateBookCommand;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final String query;
    private final Long limit;

    public ApplicationStartup(CatalogUseCase catalog,
                              @Value("${bookaro.catalog.query}") String query,
                              @Value("${bookaro.catalog.limit:15}") Long limit) {
        this.catalog = catalog;
        this.query = query;
        this.limit = limit;

    }

    @Override
    public void run(String... args) {
        init();
        findByTitle();
        findAndUpdate();
        findByTitle();
    }

    private void findAndUpdate() {
        System.out.println("Updating.....");
        catalog.findOneByTitleAndAuthor("Pan Ogniem i mieczem", "Adam Mickiewicz")
                .ifPresent(book -> {
                    UpdateBookCommand command = new UpdateBookCommand(
                            book.getId(),
                            "Pan Ogniem i Mieczem i Cyrklem",
                            book.getAuthor(),
                            book.getYear()
                    );
                    catalog.updateBook(command);
                });
    }

    private void init() {

        catalog.addBook(new CreateBookCommand("Pan Tadeusz", "Adam Mickiewicz", 1970));
        catalog.addBook(new CreateBookCommand("Pan Ogniem i mieczem", "Adam Mickiewicz", 1940));
        catalog.addBook(new CreateBookCommand("Chlopi", "Adam Mickiewicz", 1940));
        catalog.addBook(new CreateBookCommand("Pan Wolodyjowski", "Adam Mickiewicz", 1940));
    }

    private void findByTitle() {
        List<Book> byTitle = catalog.findByTitle(query);
        if (byTitle.size() > 0) {
            byTitle.stream().limit(limit).forEach(System.out::println);
        } else {
            System.out.println("Nie znaleziono w tytułach");
        }

        List<Book> byAuthor = catalog.findByAuthor(query);
        if (byAuthor.size() > 0) {
            byAuthor.stream().limit(limit).forEach(System.out::println);
        } else {
            System.out.println("Nie znaleziono w authorach");
        }
    }
}
