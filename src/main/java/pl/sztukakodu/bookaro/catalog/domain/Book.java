package pl.sztukakodu.bookaro.catalog.domain;

import lombok.*;


@RequiredArgsConstructor
@ToString
@Getter
public class Book {
    private final Long id;
    private final String title;
    private final String author;
    private final Integer year;
}
