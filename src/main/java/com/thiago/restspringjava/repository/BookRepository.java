package com.thiago.restspringjava.repository;

import com.thiago.restspringjava.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {
}
