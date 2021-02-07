package pl.sztukakodu.bookaro.domain;

import java.util.StringJoiner;

public class Book {
    private Long id;
    private String author;
    private String title;
    private Integer year;

    public Book(Long id, String title,String author, Integer year) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.year = year;
    }

    public Book() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;

    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Book.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("author='" + author + "'")
                .add("title='" + title + "'")
                .add("year=" + year)
                .toString();
    }
}
