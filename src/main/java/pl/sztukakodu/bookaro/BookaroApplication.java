package pl.sztukakodu.bookaro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class BookaroApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(BookaroApplication.class, args);
    }




    private final CatalogService catalogService;

    BookaroApplication(CatalogService catalogService){
        this.catalogService=catalogService;
    }

    @Override
    public void run(String... args) {
      //  CatalogService service = new CatalogService();
        List<Book> books = catalogService.findByTitle("Pan");
        books.forEach(System.out::println);

    }




}
