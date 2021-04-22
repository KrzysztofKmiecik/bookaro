package pl.sztukakodu.bookaro;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase;
import pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase.UpdateBookResponse;
import pl.sztukakodu.bookaro.catalog.domain.Book;
import pl.sztukakodu.bookaro.order.application.port.PlaceOrderUseCase;
import pl.sztukakodu.bookaro.order.application.port.PlaceOrderUseCase.PlaceOrderCommand;
import pl.sztukakodu.bookaro.order.application.port.PlaceOrderUseCase.PlaceOrderResponse;
import pl.sztukakodu.bookaro.order.application.port.QueryOrderUseCase;
import pl.sztukakodu.bookaro.order.domain.OrderItem;
import pl.sztukakodu.bookaro.order.domain.Recipient;

import java.math.BigDecimal;
import java.util.List;

import static pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase.CreateBookCommand;
import static pl.sztukakodu.bookaro.catalog.application.port.CatalogUseCase.UpdateBookCommand;

@Component
public class ApplicationStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final PlaceOrderUseCase placeOrder;
    private final QueryOrderUseCase queryOrder;
    private final String query;
    private final Long limit;

    public ApplicationStartup(
            CatalogUseCase catalog,
            PlaceOrderUseCase placeOrder,
            QueryOrderUseCase queryOrder,
            @Value("${bookaro.catalog.query}") String query,
            @Value("${bookaro.catalog.limit:15}") Long limit
    ) {
        this.catalog = catalog;
        this.placeOrder = placeOrder;
        this.queryOrder = queryOrder;
        this.query = query;
        this.limit = limit;

    }

    @Override
    public void run(String... args) {
        init();
        searchCatalog();
        placeOrder();
    }

    private void placeOrder() {
        //find pan tadeusz
        Book panTadeusz = catalog.findOneByTitle("Pan Tadeusz").orElseThrow(() -> new IllegalStateException("Cannot find book"));
        Book chlopi = catalog.findOneByTitle("Chlopi").orElseThrow(() -> new IllegalStateException("Cannot find a book"));

        Recipient recipient=Recipient
                .builder()
                .name("Franek")
                .city("Gdańsk")
                .email("franek@wp.pl")
                .phone("234423424")
                .street("Wiejska 55")
                .zipCode("12-123")
                .build();

        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipient)
                .item(new OrderItem(panTadeusz, 16))
                .item(new OrderItem(chlopi, 7))
                .build();

        PlaceOrderResponse response = placeOrder.placeOrder(command);
        System.out.println("Created ORDER with id: " + response.getOrderId());
        queryOrder.findAll()
                .forEach(order -> System.out.println("GOT ORDER WITH TOTAL PRICE: " + order.totalPrice() + "DETAILS: " + order));

    }

    private void searchCatalog() {
        findByTitle();
        findAndUpdate();
        findByTitle();
    }

    private void findAndUpdate() {
        System.out.println("Updating.....");
        catalog.findOneByTitleAndAuthor("Harry Pan Ogniem i mieczem", "Adam Mickiewicz")
                .ifPresent(book -> {
                    UpdateBookCommand command = UpdateBookCommand
                            .builder()
                            .id(book.getId())
                            .title("Pan Ogniem i Mieczem i Cyrklem")
                            .build();
                    UpdateBookResponse response = catalog.updateBook(command);
                    System.out.println("Updating book result: " + response.isSuccess());
                });
    }

    private void init() {

        catalog.addBook(new CreateBookCommand("Pan Tadeusz", "Adam Mickiewicz", 1970, BigDecimal.valueOf(234)));
        catalog.addBook(new CreateBookCommand("Pan Ogniem i mieczem", "Adam Mickiewicz", 1940, BigDecimal.valueOf(233)));
        catalog.addBook(new CreateBookCommand("Chlopi", "Adam Mickiewicz", 1940, BigDecimal.valueOf(11.34)));
        catalog.addBook(new CreateBookCommand("Pan Wolodyjowski", "Adam Mickiewicz", 1940, BigDecimal.valueOf(23.90)));
    }

    private void findByTitle() {
        List<Book> byTitle = catalog.findByTitle(query);
        if (byTitle.size() > 0) {
            byTitle.stream().limit(limit).forEach(System.out::println);
        } else {
            System.out.println("Nie znaleziono w tytułach");
        }

    }

    private void findByAuthor() {
        List<Book> byAuthor = catalog.findByAuthor(query);
        if (byAuthor.size() > 0) {
            byAuthor.stream().limit(limit).forEach(System.out::println);
        } else {
            System.out.println("Nie znaleziono w authorach");
        }
    }
}
