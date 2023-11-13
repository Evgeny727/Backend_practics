package com.graphql.api.shop.controllers.items;

import com.graphql.api.shop.models.entities.items.Book;
import com.graphql.api.shop.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@Component
@Controller
@CrossOrigin
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @MutationMapping
    public Book createBook(@Argument Book book) {
        return bookService.save(book);
    }

    @MutationMapping
    public Book updateBookById(@Argument Long id, @Argument Book book) {
        return bookService.updateById(id, book);
    }

    @MutationMapping
    public void deleteBookById(@Argument Long id) {
        bookService.deleteById(id);
    }

    @QueryMapping
    public Book findBookById(@Argument Long id) {
        return bookService.findById(id);
    }

    @QueryMapping
    public List<Book> findAllBooks() {
        return bookService.findAll();
    }
}
