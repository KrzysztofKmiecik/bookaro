package pl.sztukakodu.bookaro.catalog.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import pl.sztukakodu.bookaro.catalog.domain.Book;
import pl.sztukakodu.bookaro.catalog.domain.CatalogService;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class CatalogController {
    private final CatalogService catalogService;

    public List<Book> findByTitle(String title) {
        return catalogService.findByTitle(title);
    }

    public List<Book> findByAuthor(String author) {
        return catalogService.findByAuthor(author);
    }
}
