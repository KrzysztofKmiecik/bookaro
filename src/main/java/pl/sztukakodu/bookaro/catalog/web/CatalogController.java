package pl.sztukakodu.bookaro.catalog.web;


import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase;
import pl.sztukakodu.bookaro.catalog.domain.Book;

import java.util.List;

@RequestMapping("/catalog")
@RestController
@AllArgsConstructor
public class CatalogController {
    private final CatalogUseCase catalog;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAll() {
        return catalog.findAll();
    }

    //localhost:8080/catalog?title=chlopi
    @GetMapping(params = {"title"})
    public List<Book> getAllFiltered(@RequestParam String title) {
        return catalog.findByTitle(title);
    }

    //localhost:8080/catalog?title=chlopi&author=adam
    @GetMapping(params = {"title", "author"})
    public ResponseEntity<Book> getAllFiltered2(@RequestParam String title, @RequestParam String author) {
        return catalog.findOneByTitleAndAuthor(title, author)
                .map(book -> ResponseEntity.ok(book))
                .orElse(ResponseEntity.notFound().build());
    }


    //localhost:8080/catalog/1
    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return catalog.findById(id)
                .map(book -> ResponseEntity.ok(book))
                .orElse(ResponseEntity.notFound().build());
    }


}
