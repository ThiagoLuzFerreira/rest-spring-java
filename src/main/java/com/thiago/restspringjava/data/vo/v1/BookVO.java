package com.thiago.restspringjava.data.vo.v1;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@JsonPropertyOrder({"id", "author", "title", "launchDate", "price"})
public class BookVO extends RepresentationModel<BookVO> implements Serializable {

    @Mapping("id")
    @JsonProperty("id")
    private Integer key;
    private String author;
    private String title;
    private LocalDateTime launchDate;
    private Double price;

    public BookVO() {
    }

    public BookVO(Integer key, String author, String title, LocalDateTime launchDate, Double price) {
        this.key = key;
        this.author = author;
        this.title = title;
        this.launchDate = launchDate;
        this.price = price;
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
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

    public LocalDateTime getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(LocalDateTime launchDate) {
        this.launchDate = launchDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookVO bookVO = (BookVO) o;
        return Objects.equals(key, bookVO.key) && Objects.equals(author, bookVO.author) && Objects.equals(title, bookVO.title) && Objects.equals(launchDate, bookVO.launchDate) && Objects.equals(price, bookVO.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, author, title, launchDate, price);
    }
}
