package pl.sztukakodu.bookaro.catalog.web;


import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase;
import pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase.UpdateBookResponse;
import pl.sztukakodu.bookaro.catalog.domain.Book;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase.CreateBookCommand;

@RequestMapping("/catalog")
@RestController
@AllArgsConstructor
public class CatalogController {
    private final CatalogUseCase catalog;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAll(
            @RequestParam Optional<String> title,
            @RequestParam Optional<String> author,
            @RequestParam(defaultValue = "10") int limit) {

        if (title.isPresent() && author.isPresent()) {
            return catalog.findByTitleAndAuthor(title.get(), author.get());
        } else if (title.isPresent()) {
            return catalog.findByTitle(title.get());
        } else if (author.isPresent()) {
            return catalog.findByAuthor(author.get());
        }
        return catalog.findAll().stream().limit(limit).collect(Collectors.toList());

    }

    //localhost:8080/catalog/1
    @GetMapping(value = "/{id}")
    public ResponseEntity<Book> getById(@PathVariable Long id) {
        return catalog.findById(id)
                .map(book -> ResponseEntity.ok(book))
                .orElse(ResponseEntity.notFound().build());
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateBook(@PathVariable Long id, @RequestBody RestBookCommand command) {
        UpdateBookResponse response = catalog.updateBook(command.toUpdateCommand(id));
        if (!response.isSuccess()) {
            String message = String.join(", ", response.getErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,message);
        }

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addBook(@Valid @RequestBody CatalogController.RestBookCommand command) {
        Book book = catalog.addBook(command.toCreateCommand());
        URI uri = createdBookUri(book);
        return ResponseEntity.created(uri).build();
    }

    private URI createdBookUri(Book book) {
        return ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/" + book.getId().toString())
                .build()
                .toUri();
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable Long id) {
        catalog.removeById(id);
    }

    @Data
    private static class RestBookCommand {

        @NotBlank(message = "Please provide a title")
        private String title;

        @NotBlank(message = "Please provide an author")
        private String author;

        @NotNull
        private Integer year;

        @NotNull
        @DecimalMin("0.00")
        private BigDecimal price;

        CreateBookCommand toCreateCommand() {
            return new CreateBookCommand(title, author, year, price);
        }

        UpdateBookCommand toUpdateCommand(Long id) {
            return new UpdateBookCommand(id, title, author, year, price);
        }
    }

}
