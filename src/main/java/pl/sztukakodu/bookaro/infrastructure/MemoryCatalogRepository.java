package pl.sztukakodu.bookaro.infrastructure;

import org.springframework.stereotype.Repository;
import pl.sztukakodu.bookaro.domain.Book;
import pl.sztukakodu.bookaro.domain.CatalogRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryCatalogRepository implements CatalogRepository {
   private final Map<Long, Book> storage = new ConcurrentHashMap<>();


   MemoryCatalogRepository(){
       storage.put(1L, new Book(1L, "Pan Tadeusz", "Adam Mickiewicz", 1940));
       storage.put(2L, new Book(2L, "Ogniem i mieczem", "Adam Mickiewicz", 1940));
       storage.put(3L, new Book(3L, "Pan Wolodyjowski", "Adam Mickiewicz", 1940));
   }

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }
}
