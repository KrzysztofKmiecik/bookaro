package pl.sztukakodu.bookaro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BookaroApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BookaroApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        CatalogService service = new CatalogService();
        List<Book> books = service.findByTitle("Pan");
        books.forEach(System.out::println);

    }
}
