package com.thiago.restspringjava.service;

import com.thiago.restspringjava.controller.BookController;
import com.thiago.restspringjava.controller.PersonController;
import com.thiago.restspringjava.data.vo.v1.BookVO;
import com.thiago.restspringjava.exception.RequiredObjectIsNullException;
import com.thiago.restspringjava.exception.ResourceNotFoundException;
import com.thiago.restspringjava.mapper.DozerMapper;
import com.thiago.restspringjava.model.Book;
import com.thiago.restspringjava.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Service
public class BookService {

    // TODO -> VO and HATEOAS

    private Logger logger = Logger.getLogger(BookService.class.getName());

    @Autowired
    private BookRepository repository;

    public List<BookVO> findAll(){
        logger.info("Findinf all books");
        var booksVO = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
        booksVO.forEach(b -> b.add(linkTo(methodOn(BookController.class).findById(b.getKey())).withSelfRel()));
        return booksVO;
    }

    public BookVO findById(Integer id){
        logger.info("Finding a book by id");
        var bookVO = DozerMapper.parseObject(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found")), BookVO.class);
        bookVO.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return bookVO;
    }

    public BookVO create(BookVO bookVO){
        logger.info("Saving a book");
        if(bookVO != null){
            Book savedBook = repository.save(DozerMapper.parseObject(bookVO, Book.class));
            var vo = DozerMapper.parseObject(savedBook, BookVO.class);
            vo.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
            return vo;
        }
        throw new RequiredObjectIsNullException("Book object cannot be null");
    }

    public BookVO update(BookVO bookVO){
        if(bookVO == null) throw new RequiredObjectIsNullException();
        logger.info("Updating a book");

        var entity = repository.findById(bookVO.getKey()).orElseThrow(() -> new ResourceNotFoundException("Book not found to update"));
        entity.setId(bookVO.getKey());
        var book = DozerMapper.parseObject(bookVO, Book.class);
        Book saved = repository.save(book);
        BookVO vo = DozerMapper.parseObject(saved, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getKey())).withSelfRel());
        return vo;
    }

    public void delete(Integer id){
        logger.info("Deleting a book");
        findById(id);
        repository.deleteById(id);
    }
}
