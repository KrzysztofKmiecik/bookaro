package pl.sztukakodu.bookaro.catalog.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


@RequiredArgsConstructor
@ToString
@Setter
@Getter
public class Book {
    private Long id;
    private String title;
    private String author;
    private Integer year;
    private BigDecimal price;
    private String coverId;

    public Book(String title, String author, Integer year, BigDecimal price) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
    }
}
