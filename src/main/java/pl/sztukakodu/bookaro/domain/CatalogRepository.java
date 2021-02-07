package pl.sztukakodu.bookaro.domain;

import java.util.List;

public interface CatalogRepository {
    List<Book> findAll();

}
