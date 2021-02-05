package pl.sztukakodu.bookaro;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class CatalogService {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    public CatalogService(){
        storage.put(1L,new Book(1L,"Pan Tadeusz","Adam Mickiewicz",1940));
        storage.put(2L,new Book(2L,"Ogniem i mieczem","Adam Mickiewicz",1940));
        storage.put(3L,new Book(3L,"Pan Wolodyjowski","Adam Mickiewicz",1940));

    }

    List<Book> findByTitle(String title) {
        return storage.values()
                .stream()
                .filter(book -> book.getTitle().startsWith(title))
                .collect(Collectors.toList());
    }


}
